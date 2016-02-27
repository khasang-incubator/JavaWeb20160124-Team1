package io.khasang.wlogs.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) AS total FROM wlogs_users WHERE username = ?";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Boolean>() {
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                Integer count = 0;
                if (rs.next()) {
                    count = rs.getInt("total");
                }
                return count > 0;
            }
        });
    }
}
