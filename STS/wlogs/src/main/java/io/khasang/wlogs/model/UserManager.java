package io.khasang.wlogs.model;

import io.khasang.wlogs.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManager {
    final private static String ROOT_USERNAME = "root";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    public void create(UserRegistrationForm userRegistrationForm) throws Exception {
        if (!this.ROOT_USERNAME.equals(userRegistrationForm.getUsername()) ||
            this.userRepository.userExists(userRegistrationForm.getUsername()) ||
            this.userRepository.userExists(userRegistrationForm.getEmail())) {
            throw new Exception("User with such name or email already exists. Try another one.");
        }
        this.createUser(userRegistrationForm.getUsername(), passwordEncoder.encode(userRegistrationForm.getPassword()),
                        userRegistrationForm.getEmail());
    }

    private void createUser(String username, String password, String email) {
        this.jdbcTemplate.update("INSERT INTO wlogs_users(username, password, email) VALUES(?, ?, ?)", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);
            }
        });
        this.jdbcTemplate.update("INSERT INTO wlogs_user_roles(username, role) VALUES(?, 'ROLE_USER')", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, username);
            }
        });
    }
}
