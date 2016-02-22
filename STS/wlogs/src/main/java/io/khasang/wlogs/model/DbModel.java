package io.khasang.wlogs.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbModel {
    private String host = "localhost";
    private String portNumber = "3306";
    private String databaseName = "wlogs";
    private String url = "jdbc:mysql://" + host + ":" + portNumber + "/" + databaseName;
    private String userName = "root";
    private String password = "root";
    private Statement statement;
    private Connection connection;
    private String error = null;

    public DbModel() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, userName, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResultSet getSelectResult(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            //e.printStackTrace();
            setError("Ошибка: " + e);
            return null;
        }
        return resultSet;
    }
}
