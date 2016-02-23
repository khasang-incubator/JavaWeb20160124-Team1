package io.khasang.wlogs.model;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;
/**
 * @author Sergey Orlov
 * @version 21.02.2016 Singletone to get instance of JdbcTemplate
 */
 @Component
public class JdbcTempIateSingletone extends JdbcTemplate {
    private static SimpleDriverDataSource driver;
    private static JdbcTempIateSingletone jdbcTemplate;

    private JdbcTempIateSingletone(SimpleDriverDataSource driver) {
        super(driver);
    }

    /* make connection with data base via SimpleDriverDataSource  */
    static private void initConnection() {
        {
            if (driver != null) {
                return;
            } else {
                driver = new SimpleDriverDataSource();
                driver.setDriverClass(com.mysql.jdbc.Driver.class);
                driver.setUsername("root");
                driver.setUrl("jdbc:mysql://localhost/wlogs");
                driver.setPassword("root");
            }
        }
    }

    /* returns singletone instance of io.JdbcTemplateInstance */
    public static synchronized JdbcTempIateSingletone getInstance() {
        if (jdbcTemplate == null) {
            initConnection();
            return jdbcTemplate = new JdbcTempIateSingletone(driver);
        }
        System.out.println("Connection returned");
        return jdbcTemplate;
    }
}
