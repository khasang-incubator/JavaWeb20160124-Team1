package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

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

    public void deleteAll() {
        String newTable = tableName + "_" + UUID.randomUUID().toString().replace("-", "");
        String oldTable = tableName + "_" + UUID.randomUUID().toString().replace("-", "");
        String[] sql = {
                "CREATE TABLE IF NOT EXISTS " + newTable + " LIKE " + tableName,
                "LOCK TABLES " + tableName,
                "RENAME TABLE " + tableName + " TO " + oldTable + ", " + newTable + " TO " + tableName,
                "UNLOCK TABLES",
                "DROP TABLE " + oldTable
        };
        jdbcTemplate.batchUpdate(sql);
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

    public void deleteOne(final LogModel log) {
        deleteOne(log.getId());
    }

    public void deleteOne(final Integer logRecordId) {
        String sql = "DELETE FROM :tableName WHERE id = ?".replace(":tableName", tableName);
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, logRecordId);
            }
        });
    }

    public void createTable() {
        jdbcTemplate.execute(this.getCreateTableSQL());
    }

    private String getCreateTableSQL() {
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
        return sql.replace(":table_name", tableName);
    }
}
