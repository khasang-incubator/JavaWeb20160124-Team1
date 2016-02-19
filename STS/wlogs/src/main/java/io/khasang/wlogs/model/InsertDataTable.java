package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class InsertDataTable implements JdbcInterface {
    private static String sqlCheck;
    private JdbcTemplate jdbcTemplate;

    public static String getSqlCheck() {
        InsertDataTable sql = new InsertDataTable();
        sql.sqlInsert();
        return sqlCheck;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        //return;jdbcTemplate; // TODO: from bean
    }

    public void createTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS wlogs");
        jdbcTemplate.execute("create table wlogs(ID INT NOT NULL,"
                + " minute INT NOT NULL, errorLvL MEDIUMTEXT NOT NULL)");
    }

    public void sqlInsert() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("try to update db...");
        try {
            System.out.println("Creating tables");
            createTable();
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

