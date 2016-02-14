package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ViewDataTable {
    public static String sqlCheck;

    public String outData() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            System.out.println("Querying data...");
            return sqlLoadData(jdbcTemplate);
        } catch (Exception e) {
            sqlCheck = "Have error: " + e;
            System.err.println(sqlCheck);
        }
        return sqlCheck;
    }

    private String sqlLoadData(JdbcTemplate jdbcTemplate) {
        List<LogsData> results = jdbcTemplate.query(
                "select * from wlogs",
                new RowMapper<LogsData>() {
                    @Override
                    public LogsData mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new LogsData(
                                rs.getLong("ID"),
                                rs.getString("minute"),
                                rs.getString("errorLvL"));
                    }
                });
        StringBuilder stringBuilder = new StringBuilder();
        //add header
        stringBuilder.append("<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100% >");
        stringBuilder.append("<tr>");
        stringBuilder.append("<th>ID</th>");
        stringBuilder.append("<th>minute</th>");
        stringBuilder.append("<th>errorLvL</th>");
        stringBuilder.append("</tr>");
        for (LogsData logsData : results) {
            //add data
            stringBuilder.append("<tr>");
            stringBuilder.append("<td align='center'>" + logsData.getID() + "</td>");
            stringBuilder.append("<td align='center'>" + logsData.getMinute() + "</td>");
            stringBuilder.append("<td align='center'>" + logsData.getErrorLvL() + "</td>");
            stringBuilder.append("</tr>");
        }
        return stringBuilder.toString();
    }
}
