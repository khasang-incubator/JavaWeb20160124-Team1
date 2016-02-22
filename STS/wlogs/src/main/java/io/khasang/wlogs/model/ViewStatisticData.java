package io.khasang.wlogs.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ViewStatisticData {
    public static String sqlCheck=null;

    public String showStatisticData() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try{
            System.out.println("Querying data...");
            List<StatisticData> results = jdbcTemplate.query(
                    "select * from statistic",
                    new RowMapper<StatisticData>() {
                        public StatisticData mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return new StatisticData(
                                    rs.getInt("ID"),
                                    rs.getDate("time"),
                                    rs.getString("issue"),
                                    rs.getString("comment"));
                        }
                    });
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100% >");
            stringBuilder.append("<tr>");
            stringBuilder.append("<th>Id</th>");
            stringBuilder.append("<th>time</th>");
            stringBuilder.append("<th>issue</th>");
            stringBuilder.append("<th>statistic</th>");
            stringBuilder.append("</tr>");
            for (StatisticData statisticData : results) {
                stringBuilder.append("<tr>");
                stringBuilder.append("<td align='center'>" + statisticData.getId() + "</td>");
                stringBuilder.append("<td align='center'>" + statisticData.getTime() + "</td>");
                stringBuilder.append("<td align='center'>" + statisticData.getIssue() + "</td>");
                stringBuilder.append("<td align='center'>" + statisticData.getComment() + "</td>");
                stringBuilder.append("</tr>");
            }
            return stringBuilder.toString();
        }
        catch (Exception e){
            sqlCheck = "Have error: " + e;
            System.err.println(sqlCheck);
        }
        return sqlCheck;
    }
}
