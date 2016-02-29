package io.khasang.wlogs.app.config;

import io.khasang.wlogs.model.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class AppConfig {
    @Value("${jdbc.host}")
    private String jdbcHost;
    @Value("${jdbc.port}")
    private String jdbcPort;
    @Value("${jdbc.user}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;
    @Value("${jdbc.db_name}")
    private String jdbcDbName;

    @Bean(autowire = Autowire.BY_TYPE)
    public DataBaseHandler dbHandler(@Qualifier("loggerDataBaseHandler") SelfLoggerService loggerService) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.setLg(loggerService);
        return dataBaseHandler;
    }

    @Bean
    public SelfLoggerService loggerDataBaseHandler() {
        SelfLoggerService selfLoggerService = new SelfLoggerService();
        selfLoggerService.setMsg("DataBaseHandler");
        return selfLoggerService;
    }

    @Bean
    public LogRepository logRepository(JdbcTemplate jdbcTemplate) {
        LogRepository logRepository = new LogRepository();
        logRepository.setJdbcTemplate(jdbcTemplate);
        logRepository.setTableName(LogModel.tableName);
        return logRepository;
    }

    @Bean
    public DeleteDataTable deleteDataTable(LogRepository logRepository, TransactionTemplate sharedTransactionTemplate,
                                           JdbcTemplate jdbcTemplate) {
        DeleteDataTable deleteDataTable = new DeleteDataTable();
        deleteDataTable.setSharedTransactionTemplate(sharedTransactionTemplate);
        deleteDataTable.setJdbcTemplate(jdbcTemplate);
        deleteDataTable.setLogRepository(logRepository);
        deleteDataTable.setTableName(LogModel.tableName);
        return deleteDataTable;
    }

    @Bean
    public LogManager logManager(LogRepository logRepository, TransactionTemplate sharedTransactionTemplate,
                                 JdbcTemplate jdbcTemplate) {
        LogManager logManager = new LogManager();
        logManager.setSharedTransactionTemplate(sharedTransactionTemplate);
        logManager.setJdbcTemplate(jdbcTemplate);
        logManager.setLogRepository(logRepository);
        return logManager;
    }

    @Bean
    public UserManager userManager() {
        return new UserManager();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public Statistic statistic(JdbcTemplate jdbcTemplate) {
        Statistic statistic = new Statistic();
        statistic.setJdbcTemplate(jdbcTemplate);
        return statistic;
    }

    @Bean
    public InsertComment insertComment() {
        return new InsertComment();
    }

    @Bean
    public ViewStatisticData viewStatisticData() {
        return new ViewStatisticData();
    }

    @Bean
    public ViewDataTable viewDataTable() {
        return new ViewDataTable();
    }

    @Bean
    public ViewDataFromTable viewDataFromTable() {
        return new ViewDataFromTable();
    }

    @Bean(name = "productorder")
    public ProductOrder productOrder(SimpleDriverDataSource source, JdbcTemplate jdbc) {
        return new ProductOrder(source, jdbc);
    }

    @Bean
    public TransactionTemplate sharedTransactionTemplate(DataSourceTransactionManager transactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        transactionTemplate.setTimeout(30);
        return transactionTemplate;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DriverManagerDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource());
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(String.valueOf(com.mysql.jdbc.Driver.class));
        driverManagerDataSource.setUrl("jdbc:mysql://" + jdbcHost + ":" + jdbcPort + "/" + jdbcDbName);
        driverManagerDataSource.setUsername(jdbcUser);
        driverManagerDataSource.setPassword(jdbcPassword);

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setContinueOnError(true);
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        DatabasePopulatorUtils.execute(resourceDatabasePopulator, driverManagerDataSource);

        return driverManagerDataSource;
    }

    @Bean
    public SimpleDriverDataSource source() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost/logs");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbc(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
