package io.khasang.wlogs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by VSB on 13.02.2016.
 */
public class DbModel {
    private Connection connection;
    private String host = "localhost";
    private String portNumber = "3306";
    private String databaseName = "dbname";
    private String url = "jdbc:mysql://" + host + ":" + portNumber + "/" + databaseName;
    private String userName = "dbuser";
    private String password = "dbuserpassword";
    private String error;
    private Statement statement;

    public String getUrl() {
        return url;
    }

    public Connection getConnection() {
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            //e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
        return connection;
    }

    public Statement getStatement() {
        try {
            Connection connection = getConnection();
            if (connection != null)
                return connection.createStatement();
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
