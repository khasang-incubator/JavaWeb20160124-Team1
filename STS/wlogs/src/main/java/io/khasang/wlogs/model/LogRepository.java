package io.khasang.wlogs.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogRepository {
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<LogModel> findAll(Integer limit, Integer offset, String filterString) {
        String sql = "SELECT * FROM :tableName WHERE 1=1 :filter ORDER BY occurred_at DESC LIMIT :limit OFFSET :offset"
                .replace(":tableName", tableName).replace(":limit", limit.toString()).replace(":offset", offset.toString())
                .replace(":filter", filterString);
        return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<LogModel>>() {
            public ArrayList<LogModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<LogModel> logs = new ArrayList<LogModel>();
                while (rs.next()) {
                    logs.add(LogModel.createFromResultSet(rs));
                }
                return logs;
            }
        });
    }

    public Integer countAll() {
        String sql = "SELECT COUNT(*) AS total FROM :tableName".replace(":tableName", tableName);
        return jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                Integer count = 0;
                if (rs.next()) {
                    count = rs.getInt("total");
                }
                return count;
            }
        });
    }

    public ArrayList<String> getErrorSources() {
        String sql = "SELECT DISTINCT error_source FROM " + tableName;
        return getDistinctColumnStrings(sql);
    }

    public ArrayList<String> getErrorLevels() {
        String sql = "SELECT DISTINCT error_level FROM " + tableName;
        return getDistinctColumnStrings(sql);
    }

    private ArrayList<String> getDistinctColumnStrings(String sql) {
        return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<String>>() {
            @Override
            public ArrayList<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<String> errorSources = new ArrayList<String>();
                while (rs.next()) {
                    errorSources.add(rs.getString(1));
                }
                return errorSources;
            }
        });
    }
}
