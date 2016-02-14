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

    public ArrayList<LogModel> findAll(Integer limit, Integer offset) {
        String sql = "SELECT * FROM :tableName ORDER BY occurred_at DESC LIMIT :limit OFFSET :offset"
                .replace(":tableName", tableName).replace(":limit", limit.toString()).replace(":offset", offset.toString());
        return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<LogModel>>() {
            @Override
            public ArrayList<LogModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<LogModel> logs = new ArrayList<>();
                while(rs.next()) {
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
                if(rs.next()) {
                    count = rs.getInt("total");
                }
                return count;
            }
        });
    }
}
