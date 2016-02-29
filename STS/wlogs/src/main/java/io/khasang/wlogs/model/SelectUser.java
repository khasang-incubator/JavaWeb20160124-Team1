package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SelectUser implements TableObjectInterface {
    private SimpleDriverDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private int id;
    private String login;
    private String password;
    private String email;
    private String role;
    private String active;

    public SelectUser() {
    }

    public SelectUser(SimpleDriverDataSource dataSource, JdbcTemplate jdbcTemplate) {
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
        return this.jdbcTemplate.query("SELECT * FROM wlogs.users WHERE login" +
                "LIKE '%user%'", new UsersMapper());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassowrd() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getActive() {
        return active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

final class SelectedUser implements RowMapper<SelectUser> {
    public SelectUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        SelectUser selectUser = new SelectUser();
        selectUser.setId(rs.getInt("id"));
        selectUser.setLogin(rs.getString("login"));
        selectUser.setPassword(rs.getString("password"));
        selectUser.setEmail(rs.getString("email"));
        selectUser.setRole(rs.getString("role"));
        selectUser.setActive(rs.getString("active"));
        return selectUser;
    }
}