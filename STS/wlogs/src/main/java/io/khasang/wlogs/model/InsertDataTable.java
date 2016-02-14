package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class InsertDataTable {
    private static String sqlCheck;

    public static String getSqlCheck() {
        InsertDataTable sql = new InsertDataTable();
        sql.sqlInsert();
        return sqlCheck;
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
               /* jdbcTemplate.execute("DROP TABLE IF EXISTS loginfo");
                jdbcTemplate.execute("create table wlogs(ID INT NOT NULL,"
                + " minute INT NOT NULL, errorLvL MEDIUMTEXT NOT NULL)");*/
            jdbcTemplate.update("INSERT INTO loginfo(ID, errorinfo, descr) VALUES(6, 'error', 'red')");
            jdbcTemplate.update("INSERT INTO loginfo(ID, errorinfo, descr) VALUES(7, 'warn', 'yellow')");
            jdbcTemplate.update("INSERT INTO loginfo(ID, errorinfo, descr) VALUES(8, 'ok', 'green')");
            sqlCheck = "db updated";
        } catch (Exception e) {
            sqlCheck = "Have error: " + e;
            System.err.println(sqlCheck);
        }
    }

    public String sqlInsertCheck() {
        InsertDataTable sql = new InsertDataTable();
        sql.sqlInsert();
        return sqlCheck;
    }
}