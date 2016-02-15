package io.khasang.wlogs.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbModel {
    private Connection connection;
    private String host = "localhost";
    private String portNumber = "3306";
    private String databaseName = "wlogs";
    private String url = "jdbc:mysql://" + host + ":" + portNumber + "/" + databaseName;
    private String userName = "root";
    private String password = "root";
    private String error = null;

    private Connection getConnection() {
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            setError("Ошибка 001: " + e);
            return null;
        } catch (InstantiationException e) {
            setError("Ошибка 002: " + e);
            return null;
        } catch (IllegalAccessException e) {
            setError("Ошибка 003: " + e);
            return null;
        } catch (ClassNotFoundException e) {
            setError("Ошибка 004: " + e);
            return null;
        }
        return connection;
    }

    private Statement getStatement() {
        try {
            Connection connection = getConnection();
            if (connection != null)
                return connection.createStatement();
        } catch (SQLException e) {
            setError("Ошибка 005: " + e);
            return null;
        } catch (Exception e) {
            setError("Ошибка 006: " + e);
            return null;
        }
        return null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResultSet getSelectResult(String sql) {
        ResultSet resultSet = null;
        Statement statement = new DbModel().getStatement();
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            setError("Ошибка 007: " + e.toString());
            return null;
        }
        catch (Exception e){
            setError("Ошибка 008: " + e.toString());
            return null;
        }
        return resultSet;
    }
}
