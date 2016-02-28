package sec.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBCreate {
    @Autowired
    DriverManagerDataSource mDataSource;


    public boolean createUser() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(mDataSource);
        String sql = "DROP TABLE IF EXISTS users";
        jdbcTemplate.execute(sql);

        sql = "DROP TABLE IF EXISTS authorities";
        jdbcTemplate.execute(sql);

        sql = "CREATE TABLE IF NOT EXISTS users(" +
                "username VARCHAR(10) PRIMARY KEY," +
                "password VARCHAR(100)," +
                "valid  TINYINT(1)) ENGINE=InnoDB CHARACTER SET=UTF8";

        jdbcTemplate.execute(sql);
        return true;
    }

    public boolean createAuthorities() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(mDataSource);
        String sql = "CREATE TABLE IF NOT EXISTS authorities(" +
                "username VARCHAR(10)," +
                "role VARCHAR(100)) ENGINE=InnoDB CHARACTER SET=UTF8";

        jdbcTemplate.execute(sql);
        return true;
    }

    public boolean insertInfo() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(mDataSource);
        String sql = "INSERT INTO users VALUES ('1','1','1')";
        jdbcTemplate.update(sql);

        sql = "INSERT INTO users VALUES ('user','user','1')";
        jdbcTemplate.update(sql);
        sql = "INSERT INTO users VALUES ('admin','admin','1')";
        jdbcTemplate.update(sql);

        sql = "INSERT INTO authorities VALUES ('1','USER')";
        jdbcTemplate.update(sql);

        sql = "INSERT INTO authorities VALUES ('user','USER')";
        jdbcTemplate.update(sql);
        sql = "INSERT INTO authorities VALUES ('admin','ADMIN')";
        jdbcTemplate.update(sql);
        return true;
    }

    public List<UserModel> getUsers() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(mDataSource);
        String sql = "SELECT * FROM users";
        List<UserModel> users = jdbcTemplate.query(sql, new RowMapper<UserModel>() {
            public UserModel mapRow(ResultSet resultSet, int i) throws SQLException {
                return new UserModel(resultSet.getString("username"),
                                     resultSet.getString("password"),
                                     resultSet.getInt("valid"));
            }
        });
        return users;
    }
}
