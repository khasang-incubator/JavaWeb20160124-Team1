package io.khasang.wlogs.model;

import io.khasang.wlogs.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserManager {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;

    public void create(UserRegistrationForm userRegistrationForm) throws Exception {
        if (this.userRepository.userExists(userRegistrationForm.getUsername())) {
            throw new Exception("User with such name already exists. Try another one.");
        }
        this.createUser(userRegistrationForm.getUsername(), userRegistrationForm.getPassword());
    }

    private void createUser(String username, String password) {
        String[] sql = {
                "INSERT INTO wlogs_users(username, password) VALUES(?, ?)",
                "INSERT INTO wlogs_user_roles(username, role) VALUES(?, 'ROLE_USER')"
        };
        jdbcTemplate.batchUpdate(sql);
    }
}
