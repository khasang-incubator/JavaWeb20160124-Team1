package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class InsertDataTable {
    public static String sqlCheck;

    public void sqlInsert() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/eshop");
        dataSource.setPassword("toor");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("try to update db...");
        try {
            System.out.println("Creating tables");
            jdbcTemplate.execute("DROP TABLE IF EXISTS wlogs");
            jdbcTemplate.execute("create table wlogs(ID INT NOT NULL,"
                    + " minute INT NOT NULL, errorLvL MEDIUMTEXT NOT NULL)");
            jdbcTemplate.update("INSERT INTO wlogs(ID, minute, errorLvL) VALUES(1, 1, 'red')");
            jdbcTemplate.update("INSERT INTO wlogs(ID, minute, errorLvL) VALUES(2, 3, 'yellow')");
            jdbcTemplate.update("INSERT INTO wlogs(ID, minute, errorLvL) VALUES(3, 5, 'green')");
            jdbcTemplate.update("INSERT INTO wlogs(ID, minute, errorLvL) VALUES(4, 7, 'green')");
            sqlCheck = "db updated";
        } catch (Exception e) {
            sqlCheck = "Have error: " + e;
            System.err.println(sqlCheck);
        }
    }

    public String sqlInsertCheck() {
        //InsertDataTable sql = new InsertDataTable();
        //sql.sqlInsert();
        sqlInsert();
        return sqlCheck;
    }
}
