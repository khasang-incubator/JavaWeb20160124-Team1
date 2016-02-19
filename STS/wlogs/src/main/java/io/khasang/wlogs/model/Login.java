package io.khasang.wlogs.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Login implements JdbcInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sqlUnsver;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public void createTable() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
//        dataSource.setUsername("root");
//        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
//        dataSource.setPassword("root");
//        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("create table users(ID INT, login VARCHAR(40), password VARCHAR(40), description VARCHAR (100));");
    }

    public String sqlInsert() {
        createTable();
        try {
            jdbcTemplate.update("INSERT INTO users (login, password) VALUES ('user1', 'pas1');");
            jdbcTemplate.update("INSERT INTO users (login, password) VALUES ('user2', 'pas2');");
            sqlUnsver = "DB updated";
        } catch (Exception e) {
            sqlUnsver = "DB was not updated";
        }
        return sqlUnsver;
    }

    private List<User> takeUsers() {
        return jdbcTemplate.query("SELECT * FROM USERS", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setDescription(resultSet.getString(4));
                return user;
            }
        });
    }

    public String showUsers() {
        StringBuilder sb = new StringBuilder();
        List<User> users = takeUsers();
        for (User user : users) {
            sb.append("<option>" + user.getLogin() + "</option>");
        }
        return String.valueOf(sb);
    }
}
