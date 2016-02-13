package io.khasang.wlogs.service;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class DataSource {
    private static SimpleDriverDataSource dataSource = create();

    private DataSource() {
    }

    private static SimpleDriverDataSource create() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://192.168.0.250/wlogs");
        dataSource.setUsername("java_dev");
        dataSource.setPassword(".=qwert12345");
        return dataSource;
    }

    public static SimpleDriverDataSource getInstance() {
        return dataSource;
    }
}
