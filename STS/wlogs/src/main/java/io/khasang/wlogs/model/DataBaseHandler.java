package io.khasang.wlogs.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Sergey Orlov
 * @version 21.02.2016
 *          This class helps to work with data base, to create and retrieve different information from DB (see methods)
 */
@Component
public class DataBaseHandler {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private SelfLoggerService lg;

    /*variables for logging*/
    public static String sqlCheck;

    /* for test use only. should be deleted in production*/
    public void sqlInsert() {
        try {
            lg.getLog().info(lg.getTime() + " method: sqlInsert() " + " Trying to create table 'wlogs'");
            jdbcTemplate.execute("DROP TABLE IF EXISTS wlogs");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS wlogs" +
                    "(id INT NOT NULL PRIMARY KEY ," +
                    "occurred_at DATE NOT NULL," +
                    "error_source VARCHAR(20) NOT NULL," +
                    "error_level MEDIUMTEXT NOT NULL," +
                    "error_description LONGTEXT)");
            jdbcTemplate.update("INSERT INTO wlogs" +
                    " VALUES(1, '2016-02-15', 'event','DEBUG', 'Description id 1')");
            jdbcTemplate.update("INSERT INTO wlogs" +
                    " VALUES(2, '2016-01-15', 'event','DEBUG', 'Description id 2')");
            jdbcTemplate.update("INSERT INTO wlogs" +
                    " VALUES(3, '2016-02-16', 'event','DEBUG', 'Description id 3')");
            lg.getLog().info(lg.getTime() + " Table wlogs created.");

            lg.getLog().info(lg.getTime() + " method: sqlInsert() " + " Trying to create table 'typeerror'");
            jdbcTemplate.execute("DROP TABLE IF EXISTS typeerror");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS typeerror(id INT NOT NULL PRIMARY KEY," +
                    "error_level VARCHAR(10) NOT NULL," +
                    "critical VARCHAR(45) NOT NULL )");
            jdbcTemplate.update("INSERT INTO typeerror" +
                    " VALUES(1,'DEBUG','MINOR')");
            jdbcTemplate.update("INSERT INTO typeerror" +
                    " VALUES(2,'DEBUG','TRIVIAL')");
            jdbcTemplate.update("INSERT INTO typeerror" +
                    " VALUES(3,'DEBUG','MAJOR')");
            jdbcTemplate.update("INSERT INTO typeerror" +
                    " VALUES(4,'DEBUG','CRITICAL')");
            lg.getLog().info(lg.getTime() + " Table 'typeerror' created.");

            sqlCheck = " DB updated success";
        } catch (Exception e) {
            sqlCheck = lg.getTime() + "Have error: " + e;
            lg.getLog().error(sqlCheck);

        }
    }

    /* for test use only. should be deleted in production*/
    public String sqlInsertCheck() {
        sqlInsert();
        lg.getLog().info(lg.getTime() + " Finished with result: " + sqlCheck);
        return sqlCheck;
    }

    /**
     * @return List<LogModel> , LogModel.class represents one row of the table 'wlogs'
     */
    public List<LogModel> getWlogsTableContent() {

        String sql = "SELECT * FROM wlogs";

        lg.getLog().info(lg.getTime() + " method: getWlogsTableContent() " + "SQL: " + sql);
        RowMapper<LogModel> mapper = new RowMapper<LogModel>() {
            public LogModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return LogModel.createFromResultSet(resultSet);
            }
        };
        List<LogModel> tableContent = jdbcTemplate.query(sql, mapper);
        lg.getLog().info(lg.getTime() + " List form query 'wlogs' created success");
        return tableContent;
    }

    /**
     * @return List<TypeErrorModel> , TypeErrorModel.class represents one row of the table 'typeerror'
     */
    public List<TypeErrorModel> getTypeerrorTableContent() {

        String sql = "SELECT * FROM typeerror";
        lg.getLog().info(lg.getTime() + " method: getTypeerrorTableContent() SQL: " + sql);
        RowMapper<TypeErrorModel> mapper = new RowMapper<TypeErrorModel>() {
            public TypeErrorModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return TypeErrorModel.createFromResultSet(resultSet);
            }
        };
        List<TypeErrorModel> tableContent = jdbcTemplate.query(sql, mapper);
        lg.getLog().info(lg.getTime() + " List form query 'typeerror' created success");
        return tableContent;
    }

    /**
     * @return list of table names exist in data base 'wlogs'
     */
    private List<String> makeTableNamesArr() {

        String sql = "SHOW TABLES";
        lg.getLog().info(lg.getTime() + " method: makeTableNamesArr() SQL: " + sql);
        RowMapper<String> mapper = new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        };
        List<String> names = jdbcTemplate.query(sql, mapper);
        lg.getLog().info(lg.getTime() + " Making list of table names result OK");
        return names;
    }

    /**
     * @return return tableName by index
     */
    public String getTableName(int index) {
        List<String> names = makeTableNamesArr();
        lg.getLog().info(lg.getTime() + " method: getTableName(int index) Table name '" + names.get(index) + "' returned");
        return names.get(index);
    }

    /**
     * @return return joined tables 'wlogs' and 'typerror' by natural join(by id)
     */
    public List<TypeErrorModel> joinTables(int[] tblNumber) {
        if (tblNumber.length < 2) {
            throw new NullPointerException("Вы выбрали только одну таблицу." +
                    "Вы должны выбрать не менее двух таблиц для объединения. Вернитесь назад и сделайте выбор еще раз");
        }
        String sql = String.format("SELECT * FROM %s NATURAL JOIN %s", getTableName(tblNumber[0]), getTableName(tblNumber[1]));
        lg.getLog().info(lg.getTime() + " method: joinTables(int[]) SQL: " + sql);
        RowMapper<TypeErrorModel> mapper = new RowMapper<TypeErrorModel>() {
            public TypeErrorModel mapRow(ResultSet resultSet, int i) throws SQLException {
                return TypeErrorModel.createFromResultSet(resultSet);
            }
        };
        List<TypeErrorModel> joinedTables = jdbcTemplate.query(sql, mapper);
        lg.getLog().info(lg.getTime() + " Making list of joined columns result OK");
        return joinedTables;
    }

    public void setLg(SelfLoggerService lg) {
        this.lg = lg;
    }
}

