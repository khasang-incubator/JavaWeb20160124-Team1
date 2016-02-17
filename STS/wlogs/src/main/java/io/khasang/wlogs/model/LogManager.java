package io.khasang.wlogs.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.UUID;
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
    public final int DELETE_OLDER_ONE_WEEK = 7;
    public final int DELETE_OLDER_TWO_WEEK = 8;
    public final int DELETE_OLDER_THREE_WEEK = 9;

    private String tableName;
    private JdbcTemplate jdbcTemplate;
    private LogRepository logRepository;
    private TransactionTemplate sharedTransactionTemplate;
    private DeleteDataTable deleteDataTable;

    public void setDeleteDataTable(DeleteDataTable deleteDataTable) {
        this.deleteDataTable = deleteDataTable;
    }

    public void setSharedTransactionTemplate(TransactionTemplate sharedTransactionTemplate) {
        this.sharedTransactionTemplate = sharedTransactionTemplate;
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LinkedHashMap<Integer, String> getAvailableDateCriteria() {
        LinkedHashMap<Integer, String> criteriaMap = new LinkedHashMap<Integer, String>();
        criteriaMap.put(DELETE_ALL_EXCEPT_LAST_N, "Оставить последние 1000 записей");
        criteriaMap.put(DELETE_OLDER_ONE_WEEK, "Удалить записи старше одной недели");
        criteriaMap.put(DELETE_OLDER_TWO_WEEK, "Удалить записи старше двух недель");
        criteriaMap.put(DELETE_OLDER_THREE_WEEK, "Удалить записи старше трех недель");
        criteriaMap.put(DELETE_OLDER_ONE_MONTH, "Удалить записи старше одного месяца");
        criteriaMap.put(DELETE_OLDER_THREE_MONTH, "Удалить записи старше трех месяцев");
        criteriaMap.put(DELETE_OLDER_SIX_MONTH, "Удалить записи старше шести месяцев");
        criteriaMap.put(DELETE_OLDER_NINE_MONTH, "Удалить записи старше девяти месяцев");
        criteriaMap.put(DELETE_OLDER_ONE_YEAR, "Удалить записи старше одного года");
        return criteriaMap;
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
            case DELETE_OLDER_ONE_WEEK:
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                break;
            case DELETE_OLDER_TWO_WEEK:
                calendar.add(Calendar.DAY_OF_MONTH, -14);
                break;
            case DELETE_OLDER_THREE_WEEK:
                calendar.add(Calendar.DAY_OF_MONTH, -21);
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
                "DELETE FROM :tableName WHERE occurred_at < ?".replace(":tableName", tableName),
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setDate(1, limit);
                    }
                }
        );
    }

    public int deleteAllExceptLastNRecords(final int recordsAmountToKeepAlive) throws DataAccessException {
        Integer logRecordsCountTotal = logRepository.countAll();
        if (recordsAmountToKeepAlive >= logRecordsCountTotal) {
            return 0;
        }
        final String temporaryTableName = tableName + "_" + UUID.randomUUID().toString().replace("-", "");
        sharedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS :temporaryTableName AS (SELECT * FROM :tableName ORDER BY occurred_at DESC LIMIT :limit)"
                        .replace(":tableName", tableName).replace(":temporaryTableName", temporaryTableName)
                        .replace(":limit", String.valueOf(recordsAmountToKeepAlive)));
                jdbcTemplate.execute("DELETE FROM :tableName".replace(":tableName", tableName));
                jdbcTemplate.execute("INSERT INTO :tableName SELECT * FROM :temporaryTableName"
                        .replace(":tableName", tableName).replace(":temporaryTableName", temporaryTableName));
                jdbcTemplate.execute("DROP TABLE :temporaryTableName".replace(":temporaryTableName", temporaryTableName));
            }
        });
        return logRecordsCountTotal - recordsAmountToKeepAlive;
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
                ") ENGINE=INNODB, DEFAULT CHARACTER SET=UTF8";
        sql = sql.replace(":table_name", tableName);
        jdbcTemplate.execute(sql);
    }

    public void loadFixtures() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            // TODO: bad idea.... how to calculate lines in the file?
            LineNumberReader readerTmp = new LineNumberReader(new BufferedReader(new FileReader(loader.getResource("dev.log").getFile())));
            Stream<String> linesTmp = readerTmp.lines();
            final Integer linesCount = (int) linesTmp.count();
            linesTmp.close();
            readerTmp.close();
            // -----
            LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(loader.getResource("dev.log").getFile())));
            final Stream<String> lines = reader.lines();
            final Iterator<String> linesIterator = lines.iterator();
            final Pattern pattern = Pattern.compile("^\\[(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\]\\s([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+):\\s(.*)");
            final String sql = "INSERT INTO :tableName(occurred_at, error_level, error_source, error_description) VALUES(?,?,?,?)"
                    .replace(":tableName", tableName);
            sharedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
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
                            return linesCount;
                        }
                    });
                }
            });
            lines.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
