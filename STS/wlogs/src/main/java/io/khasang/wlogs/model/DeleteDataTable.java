package io.khasang.wlogs.model;

import io.khasang.wlogs.form.DeleteDataForm;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class DeleteDataTable {
    private TransactionTemplate sharedTransactionTemplate;
    private JdbcTemplate jdbcTemplate;
    private String tableName;
    private LogRepository logRepository;

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
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
                "RENAME TABLE " + tableName + " TO " + oldTable + ", " + newTable + " TO " + tableName,
                "DROP TABLE " + oldTable
        };
        jdbcTemplate.batchUpdate(sql);
    }

    public Integer deleteByDateInterval(DeleteDataForm.DateIntervalType dateIntervalType, final Integer dateIntervalSize) {
        String sql = "DELETE FROM " + tableName + " WHERE occurred_at < DATE_SUB(CURDATE(), INTERVAL ? " + dateIntervalType.toString() + ")";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, dateIntervalSize);
            }
        });
    }

    public Integer deleteByCriteria(final String errorSource, final String errorLevel,
                                    final DeleteDataForm.DateIntervalType dateIntervalType, final Integer dateIntervalSize) {
        // TODO: research java db criteria...
        final LinkedList<String> filters = new LinkedList<String>();
        if (null != errorSource) {
            filters.add("error_source = ?");
        }
        if (null != errorLevel) {
            filters.add("error_level = ?");
        }
        if (null != dateIntervalType) {
            String filterOccurredAt = "occurred_at < DATE_SUB(CURDATE(), INTERVAL ? " + dateIntervalType.name() + ")";
            filters.add(filterOccurredAt);
        }
        if (filters.size() == 0) {
            return 0;
        }
        String sql = "DELETE FROM " + tableName + " WHERE " + String.join(" AND ", filters);
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                Integer parameterIndex = 1;
                if (null != errorSource) {
                    ps.setString(parameterIndex++, errorSource);
                }
                if (null != errorLevel) {
                    ps.setString(parameterIndex++, errorLevel);
                }
                if (null != dateIntervalType) {
                    ps.setInt(parameterIndex, dateIntervalSize);
                }
            }
        });
    }

    public int deleteAllExceptLastNRecords(final int recordsAmountToKeepAlive) throws DataAccessException {
        Integer logRecordsCountTotal = logRepository.countAll();
        if (recordsAmountToKeepAlive >= logRecordsCountTotal) {
            return 0;
        }
        final String temporaryTableNameNew = tableName + "_" + UUID.randomUUID().toString().replace("-", "");
        final String temporaryTableNameOld = tableName + "_" + UUID.randomUUID().toString().replace("-", "");
        sharedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Integer limit = recordsAmountToKeepAlive;
                String[] sql = {
                        "CREATE TABLE IF NOT EXISTS " + temporaryTableNameNew + " LIKE " + tableName,
                        "INSERT INTO " + temporaryTableNameNew + " SELECT * FROM " + tableName + " ORDER BY occurred_at DESC LIMIT " + limit,
                        "RENAME TABLE " + tableName + " TO " + temporaryTableNameOld + ", " + temporaryTableNameNew + " TO " + tableName,
                        "DROP TABLE " + temporaryTableNameOld
                };
                jdbcTemplate.batchUpdate(sql);
            }
        });
        return logRecordsCountTotal - recordsAmountToKeepAlive;
    }

    public void deleteOne(final LogModel log) {
        deleteOne(log.getId());
    }

    public void deleteOne(final Integer logRecordId) {
        String sql = "DELETE FROM :tableName WHERE id = ?".replace(":tableName", tableName);
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
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
