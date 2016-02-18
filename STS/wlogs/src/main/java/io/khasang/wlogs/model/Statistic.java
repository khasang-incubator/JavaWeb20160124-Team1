package io.khasang.wlogs.model;

import com.sun.javafx.collections.MappingChange;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.w3c.dom.Text;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Statistic implements JdbcInterface {
    private JdbcTemplate jdbcTemplate;

    public Statistic() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost/wlogs");
        dataSource.setPassword("root");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF not EXISTS statistic" +
                "(`id` INT NOT NULL AUTO_INCREMENT," +
                "`server` VARCHAR(45) NULL," +
                "`date` DATETIME NULL," +
                "`issue` TEXT NULL," +
                "`comment` TEXT NULL," +
                "PRIMARY KEY (`id`)," +
                "UNIQUE INDEX `id_UNIQUE` (`id` ASC))" +
                "ENGINE = InnoDB" +
                "DEFAULT CHARACTER SET = utf8;"
        );
    }
    public void clearTable() {
        jdbcTemplate.execute("delete from statistic");
    }
    public void insertDataToTable(){
        jdbcTemplate.execute("insert into statistic" +
                "(`server`,`date`,`issue`)" +
                "select id as `server`" +
                ",occurred_at as `date`" +
                ",error_description as `issue`" +
                "from wlogs");
    }
    public List<StatisticModel> getStatistic(){

        String sql = "SELECT * FROM statistic";
        List<StatisticModel> statisticModelList = new ArrayList<StatisticModel>();
        List<Map<String,Object>> rows =  jdbcTemplate.queryForList(sql);

        for (Map row : rows) {
            StatisticModel statisticModel = new StatisticModel();
            statisticModel.setServer((String)(row.get("server")));
            statisticModel.setDate((Timestamp)row.get("date"));
            statisticModel.setIssue((String) row.get("issue"));
            statisticModelList.add(statisticModel);
        }
        return statisticModelList;
    }
}
