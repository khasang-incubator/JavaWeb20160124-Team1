package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteDataTable {
    private TransactionTemplate sharedTransactionTemplate;
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    enum DateIntervalType {
        DAY, WEEK, MONTH, YEAR;
    }

    public void setSharedTransactionTemplate(TransactionTemplate sharedTransactionTemplate) {
        this.sharedTransactionTemplate = sharedTransactionTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer deleteByCriteria(final String errorSource, final String errorLevel, DateIntervalType dateIntervalType, final Integer dateIntervalSize) {
        String sql = "DELETE FROM :tableName WHERE error_source = ? AND error_level = ? AND occurred_at < DATE_SUB(CURDATE(), INTERVAL ? :dateIntervalType)"
                .replace(":tableName", tableName).replace(":dateIntervalType", dateIntervalType.toString());
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, errorSource);
                ps.setString(2, errorLevel);
                ps.setInt(3, dateIntervalSize);
            }
        });
    }

    private void deleteOne(final LogModel log) {
        deleteOne(log.getId());
    }

    private void deleteOne(final Integer logRecordId) {
        String sql = "DELETE FROM :tableName WHERE id = ?".replace(":tableName", tableName);
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, logRecordId);
            }
        });
    }
}
