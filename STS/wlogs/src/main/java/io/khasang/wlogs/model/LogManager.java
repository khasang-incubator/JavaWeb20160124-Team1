package io.khasang.wlogs.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogManager {
    private static final int RECORDS_COUNT_TO_KEEP_ALIVE = 1000;

    public final int DELETE_ALL_EXCEPT_LAST_N = 1;
    public final int DELETE_OLDER_ONE_MONTH = 2;
    public final int DELETE_OLDER_THREE_MONTH = 3;
    public final int DELETE_OLDER_SIX_MONTH = 4;
    public final int DELETE_OLDER_NINE_MONTH = 5;
    public final int DELETE_OLDER_ONE_YEAR = 6;

    private String tableName;
    private JdbcTemplate jdbcTemplate;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int delete(int filterType) throws SQLException {
        if (DELETE_ALL_EXCEPT_LAST_N == filterType) {
            return deleteAllExceptLastNRecords(RECORDS_COUNT_TO_KEEP_ALIVE);
        }

        java.util.Date end = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        switch (filterType) {
            case DELETE_OLDER_NINE_MONTH:
                calendar.add(Calendar.MONTH, -9);
                break;
            case DELETE_OLDER_ONE_MONTH:
                calendar.add(Calendar.MONTH, -1);
                break;
            case DELETE_OLDER_THREE_MONTH:
                calendar.add(Calendar.MONTH, -3);
                break;
            case DELETE_OLDER_SIX_MONTH:
                calendar.add(Calendar.MONTH, -6);
                break;
            case DELETE_OLDER_ONE_YEAR:
                calendar.add(Calendar.YEAR, -1);
                break;
            default:
                throw new RuntimeException("Invalid filter type");
        }
        return deleteAllByDateRange(new Date(calendar.getTime().getTime()));
    }

    public boolean deleteOne(final int logModelId) {
        return 1 == jdbcTemplate.update(
                "DELETE FROM :tableName WHERE id = ?".replace(":tableName", tableName),
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setInt(1, logModelId);
                    }
                }
        );
    }

    public int deleteAllByDateRange(final Date limit) throws SQLException {
        return jdbcTemplate.update(
                "DELETE FROM :tableName WHERE occured_at < ?".replace(":tableName", tableName),
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setDate(1, limit);
                    }
                }
        );
    }

    public int deleteAllExceptLastNRecords(int recordsAmountToKeepAlive) throws DataAccessException {
        final Integer recordId = jdbcTemplate.query(
                "SELECT id as recordId FROM :tableName ORDER BY id DESC LIMIT 1 OFFSET :offset"
                        .replace(":tableName", tableName)
                        .replace(":offset", String.valueOf(recordsAmountToKeepAlive)),
                new ResultSetExtractor<Integer>() {
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.first()) {
                            return rs.getInt(1);
                        }
                        return 0;
                    }
                }
        );
        return jdbcTemplate.update("DELETE FROM wlogs WHERE wlogs.id < ?", new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, recordId);
            }
        });
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS :table_name (\n" +
                "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "  occurred_at DATETIME NOT NULL,\n" +
                "  error_level VARCHAR(20) NOT NULL,\n" +
                "  error_source VARCHAR(30) NOT NULL,\n" +
                "  error_description TEXT NOT NULL,\n" +
                "  PRIMARY KEY (id),\n" +
                "  INDEX occurred_at_idx (occurred_at),\n" +
                "  INDEX error_level_idx (error_level),\n" +
                "  INDEX error_source_idx (error_source)\n" +
                ")".replace(":table_name", tableName);
        jdbcTemplate.execute(sql);
    }

    public void loadFixtures() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            final Stream<String> lines = new BufferedReader(new FileReader(loader.getResource("dev.log").getFile())).lines();
            final Iterator<String> linesIterator = lines.iterator();
            final Pattern pattern = Pattern.compile("^\\[(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\]\\s([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+):\\s(.*)");
            String sql = "INSERT INTO :tableName(occurred_at, error_level, error_source, error_description) VALUES(?,?,?,?)"
                            .replace(":tableName", tableName);
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    if (linesIterator.hasNext()) {
                        String line = linesIterator.next();
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.matches()) {
                            final String occurredAt = matcher.group(1);
                            final String errorSource = matcher.group(2);
                            final String errorLevel = matcher.group(3);
                            final String errorDescription = matcher.group(4);
                            DateFormat formatter = new SimpleDateFormat("yy-MM-dd h:m:s");
                            try {
                                java.util.Date occurredAtDate = formatter.parse(occurredAt);
                                ps.setDate(1, new Date(occurredAtDate.getTime()));
                                ps.setString(2, errorLevel);
                                ps.setString(3, errorSource);
                                ps.setString(4, errorDescription);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                public int getBatchSize() {
                    return (int)lines.count();
                }
            });
            lines.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
