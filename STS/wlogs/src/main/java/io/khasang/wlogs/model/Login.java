package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Albot D.
 */
public class Login {
    JdbcTemplate jdbcTemplate;
    private String sqlAnswer;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Login() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private List<User> takeUsers() {
        try {
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
        } catch (Exception e) {
            sqlAnswer = "smth wrong";
        }
        return null;
    }

    public String showUsers() {
        StringBuilder sb = new StringBuilder();
        List<User> users = takeUsers();
        if (users != null) {
            for (User user : users) {
                sb.append("<option>" + user.getLogin() + "</option>");
            }
            return String.valueOf(sb);
        } else {
            sb.append("<option>-no users-</option>");
        }
        return String.valueOf(sb);
    }
}
