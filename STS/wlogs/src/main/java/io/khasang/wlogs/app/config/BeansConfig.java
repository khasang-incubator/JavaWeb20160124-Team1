package io.khasang.wlogs.app.config;

import io.khasang.wlogs.model.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class BeansConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate sharedTransactionTemplate;

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
    public LogRepository logRepository() {
        LogRepository logRepository = new LogRepository();
        logRepository.setJdbcTemplate(this.jdbcTemplate);
        logRepository.setTableName(LogModel.tableName);
        return logRepository;
    }

    @Bean
    public DeleteDataTable deleteDataTable(@Qualifier("logRepository") LogRepository logRepository) {
        DeleteDataTable deleteDataTable = new DeleteDataTable();
        deleteDataTable.setSharedTransactionTemplate(this.sharedTransactionTemplate);
        deleteDataTable.setJdbcTemplate(this.jdbcTemplate);
        deleteDataTable.setLogRepository(logRepository);
        deleteDataTable.setTableName(LogModel.tableName);
        return deleteDataTable;
    }

    @Bean
    public LogManager logManager(@Qualifier("logRepository") LogRepository logRepository) {
        LogManager logManager = new LogManager();
        logManager.setSharedTransactionTemplate(this.sharedTransactionTemplate);
        logManager.setJdbcTemplate(this.jdbcTemplate);
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
    public Statistic statistic() {
        Statistic statistic = new Statistic();
        statistic.setJdbcTemplate(this.jdbcTemplate);
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
}
