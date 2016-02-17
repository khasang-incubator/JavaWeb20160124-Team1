package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class InsertComment implements JdbcInterface {
    private static String sqlCheck;
    private JdbcTemplate jdbcTemplate;

    public void sqlInsertComment() {
        String comment = "something"; // TODO: 17.02.2016 задание строки юзером
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("checking connection...");
        try{
            System.out.println("getting current statistic table...");
            createTable();
            //jdbcTemplate.update("SELECT * FROM statistic");
            System.out.println("Insert comment");
            jdbcTemplate.update("INSERT INTO statistic (comment) VALUE (?)", comment);
        }
        catch (Exception e){
            sqlCheck = "Have error " + e;
            System.err.println(sqlCheck);
        }
    }

    public String sqlInsertCheck() {
        sqlInsertComment();
        return sqlCheck;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

    }

    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS statistic(ID INT NOT NULL," +
                " time DATETIME NOT NULL," +
                " issue MEDIUMTEXT NOT NULL," +
                " comment LONGTEXT)");
    }
}
