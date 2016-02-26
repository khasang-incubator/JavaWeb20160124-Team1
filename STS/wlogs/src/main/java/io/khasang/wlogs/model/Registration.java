package io.khasang.wlogs.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Albot D.
 */
public class Registration {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private String sqlAnswer;

    public Registration() {
    }

    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(ID INT, login VARCHAR(40), password VARCHAR(40), email VARCHAR (100), active TINYINT(1), role VARCHAR (100));");
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
