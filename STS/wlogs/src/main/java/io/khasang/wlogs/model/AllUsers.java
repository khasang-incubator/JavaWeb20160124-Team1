package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AllUsers implements TableObjectInterface {
    private SimpleDriverDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private int id;
    private String occurredAt;
    private String errorLevel;
    private String errorSource;
    private String errorDescription;

    public AllUsers() {
    }

    public AllUsers(SimpleDriverDataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    public SimpleDriverDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(SimpleDriverDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List selectWholeTable() throws SQLException {
        return this.jdbcTemplate.query("SELECT id, occurred_at, error_level, error_source, error_description " +
                "FROM wlogs.wlogs", new ItemMapper());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getOccurredAt() {
        return occurredAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOccurredAt(String occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    public String getErrorSource() {
        return errorSource;
    }

    @Override
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}

final class ItemMapper implements RowMapper<AllUsers> {
    public AllUsers mapRow(ResultSet rs, int rowNum) throws SQLException {
        AllUsers allUsers = new AllUsers();
        allUsers.setId(rs.getInt("id"));
        allUsers.setOccurredAt(rs.getString("occurred_at"));
        allUsers.setErrorLevel(rs.getString("error_level"));
        allUsers.setErrorSource(rs.getString("error_source"));
        allUsers.setErrorDescription(rs.getString("error_description"));
        return allUsers;
    }
}