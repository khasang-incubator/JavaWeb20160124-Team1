package io.khasang.wlogs.model;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Albot D.
 */
public class Registration {
    JdbcTemplate jdbcTemplate;
    private String sqlAnswer;

    public Registration() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(ID INT, login VARCHAR(40), password VARCHAR(40), description VARCHAR (100));");
    }

    public String sqlInsert(final String username, final String password) {
        try {
            createTable();
            jdbcTemplate.update("INSERT INTO users (login, password) VALUES (?, ?);", new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                }
            });
            sqlAnswer = "DB updated";
        } catch (Exception e) {
            sqlAnswer = "DB was not updated";
        }
        return sqlAnswer;
    }
}
