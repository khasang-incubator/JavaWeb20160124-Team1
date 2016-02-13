package io.khasang.wlogs.model;

import io.khasang.wlogs.service.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class CreateLogsTable {
    public void doCreate() {
        String sql = "CREATE TABLE IF NOT EXISTS wlogs (\n" +
                "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "  occurred_at DATETIME NOT NULL,\n" +
                "  error_level VARCHAR(20) NOT NULL,\n" +
                "  error_source VARCHAR(30) NOT NULL,\n" +
                "  error_description TEXT NOT NULL,\n" +
                "  PRIMARY KEY (id),\n" +
                "  INDEX occurred_at_idx (occurred_at),\n" +
                "  INDEX error_level_idx (error_level),\n" +
                "  INDEX error_source_idx (error_source)\n" +
                ")";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSource.getInstance());
        jdbcTemplate.execute(sql);
    }
}
