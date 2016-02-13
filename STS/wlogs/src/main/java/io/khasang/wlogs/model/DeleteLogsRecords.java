package io.khasang.wlogs.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class DeleteLogsRecords {
    public final int DELETE_ALL_EXCEPT_LAST_N = 1;
    public final int DELETE_OLDER_ONE_MONTH = 2;
    public final int DELETE_OLDER_THREE_MONTH = 3;
    public final int DELETE_OLDER_SIX_MONTH = 4;
    public final int DELETE_OLDER_NINE_MONTH = 5;
    public final int DELETE_OLDER_ONE_YEAR = 6;

    public final int RECORDS_COUNT_TO_KEEP_ALIVE = 100;

    public void deleteLogRecords(int filterType) throws SQLException {
        java.util.Date end = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        switch (filterType) {
            case DELETE_ALL_EXCEPT_LAST_N:
                deleteAllExceptLastNRecords(RECORDS_COUNT_TO_KEEP_ALIVE);
                break;
            case DELETE_OLDER_NINE_MONTH:
                calendar.add(Calendar.MONTH, -9);
                deleteAllByDateRange(new Date(calendar.getTime().getTime()));
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
    }

    public void deleteAllByDateRange(final Date limit) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.execute(
            "DELETE FROM wlogs WHERE occured_at > ?",
            new PreparedStatementCallback<Boolean>() {
                public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                    ps.setDate(1, limit);
                    return ps.execute();
                }
            }
        );
    }

    public void deleteAllExceptLastNRecords(int recordsAmountToKeepAlive) throws DataAccessException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        final Integer recordId = jdbcTemplate.query(
                "SELECT id as recordId FROM wlogs ORDER BY id DESC LIMIT 1 OFFSET :offset"
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
        jdbcTemplate.execute(
                "DELETE FROM wlogs WHERE wlogs.id < ?",
                new PreparedStatementCallback<Boolean>() {
                    public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        ps.setInt(1, recordId);
                        return ps.execute();
                    }
                }
        );
    }
}
