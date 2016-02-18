package io.khasang.wlogs.model;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.LinkedList;
import java.util.List;

public class Login implements JdbcInterface {
    JdbcTemplate jdbcTemplate;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

    }

    @Override
    public void createTable() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("create table users(ID INT, login VARCHAR(40), password VARCHAR(40), description VARCHAR (100));");
        jdbcTemplate.update("INSERT INTO users (login, password) VALUES ('user1', 'pas1');");
        jdbcTemplate.update("INSERT INTO users (login, password) VALUES ('user2', 'pas2');");
    }

//    public List<User> takeUsers() {
//        List<User> users = new LinkedList<User>();
//        jdbcTemplate.
//    }
}
