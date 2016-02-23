package io.khasang.wlogs.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    JdbcTempIateSingletone mJdbcTemplateInstance;

    /*variables for logging*/
    Logger logger = LoggerFactory.getLogger("DataBaseHandler");
    Date currentDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:S");
    String time = sdf.format(currentDate);
    public static String sqlCheck;


    public void sqlInsert() {
        logger.info(time + " Initialization of JdbcTemplate");

        logger.info(time + " Trying to create tables");
        try {
            logger.info(time + " trying to create table 'wlogs'");
            mJdbcTemplateInstance.execute("DROP TABLE IF EXISTS wlogs");
            mJdbcTemplateInstance.execute("CREATE TABLE IF NOT EXISTS wlogs" +
                    "(id INT NOT NULL PRIMARY KEY ," +
                    "occurred_at DATE NOT NULL," +
                    "error_source VARCHAR(20) NOT NULL," +
                    "error_level MEDIUMTEXT NOT NULL," +
                    "error_description LONGTEXT)");
            mJdbcTemplateInstance.update("INSERT INTO wlogs" +
                    " VALUES(1, '2016-02-15', 'event','DEBUG', 'Description id 1')");
            mJdbcTemplateInstance.update("INSERT INTO wlogs" +
                    " VALUES(2, '2016-01-15', 'event','DEBUG', 'Description id 2')");
            mJdbcTemplateInstance.update("INSERT INTO wlogs" +
                    " VALUES(3, '2016-02-16', 'event','DEBUG', 'Description id 3')");
            logger.info(time + " Table wlogs created.");
            logger.info(time + " Trying to create table 'typeerror'");

            mJdbcTemplateInstance.execute("DROP TABLE IF EXISTS typeerror");
            mJdbcTemplateInstance.execute("CREATE TABLE IF NOT EXISTS typeerror(id INT NOT NULL PRIMARY KEY," +
                    "error_level VARCHAR(10) NOT NULL," +
                    "critical VARCHAR(45) NOT NULL )");
            mJdbcTemplateInstance.update("INSERT INTO typeerror" +
                    " VALUES(1,'DEBUG','MINOR')");
            mJdbcTemplateInstance.update("INSERT INTO typeerror" +
                    " VALUES(2,'DEBUG','TRIVIAL')");
            mJdbcTemplateInstance.update("INSERT INTO typeerror" +
                    " VALUES(3,'DEBUG','MAJOR')");
            mJdbcTemplateInstance.update("INSERT INTO typeerror" +
                    " VALUES(4,'DEBUG','CRITICAL')");
            logger.info(time + " Table 'typeerror' created.");

            sqlCheck = " DB updated success";
            logger.info(time + sqlCheck);
        } catch (Exception e) {
            sqlCheck = time + "Have error: " + e;
            logger.error(sqlCheck);

        }
    }

    public String sqlInsertCheck() {
        sqlInsert();
        logger.info(time + " Task finished with result: " + sqlCheck);
        return sqlCheck;
    }

    /**
     * @return List<LogModel> , LogModel.class represents one row of the table 'wlogs'
     */
    public List<LogModel> getWlogsTableContent() {

        String sql = "SELECT * FROM wlogs";

        logger.info(time + " Creating mapper");
        RowMapper<LogModel> mapper = new RowMapper<LogModel>() {
            public LogModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return LogModel.createFromResultSet(resultSet);
            }
        };
        logger.info("Querying DB");
        List<LogModel> tableContent = mJdbcTemplateInstance.query(sql, mapper);

        logger.info(time + " List form query 'wlogs' created success");

        return tableContent;
    }

    /**
     * @return List<TypeErrorModel> , TypeErrorModel.class represents one row of the table 'typeerror'
     */
    public List<TypeErrorModel> getTypeerrorTableContent() {

        String sql = "SELECT * FROM typeerror";

        logger.info(time + " Creating mapper");
        RowMapper<TypeErrorModel> mapper = new RowMapper<TypeErrorModel>() {
            public TypeErrorModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return TypeErrorModel.createFromResultSet(resultSet);
            }
        };
        logger.info("Querying DB");
        List<TypeErrorModel> tableContent = mJdbcTemplateInstance.query(sql, mapper);

        logger.info(time + " List form query 'wlogs' created success");

        return tableContent;
    }

    /**
     * @return list of table name exist in data base 'wlogs'*/
    private List<String> makeTableNamesArr() {
        logger.info(time + " Init JdbcTemplate");

        String sql = "SHOW TABLES";
        logger.info(time + " Making mapper");
        RowMapper<String> mapper = new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        };
        logger.info(time + "Making list of table names");
        List<String> names = mJdbcTemplateInstance.query(sql, mapper);
        logger.info(time + "Making list of table names result OK");
        return names;
    }

    /**@return return tableName by index*/
    public String getTableName(int index) {
        List<String> names = makeTableNamesArr();
        return names.get(index);
    }

    /** @return return joined tables by natural join(by id)*/
    public List<TypeErrorModel> joinTables(int[] tblNumber) {
        if (tblNumber.length < 2) {
            throw new NullPointerException("Вы выбрали только одну таблицу." +
                    "Вы должны выбрать не менее двух таблиц для объединения. Вернитесь назад и сделайте выбор еще раз");
        }
        logger.info(time + " Init JdbcTemplate");
        String sql = String.format("SELECT * FROM %s NATURAL JOIN %s", getTableName(tblNumber[0]), getTableName(tblNumber[1]));
        System.out.println(sql);
        logger.info(time + " Making mapper");

        RowMapper<TypeErrorModel> mapper = new RowMapper<TypeErrorModel>() {
            public TypeErrorModel mapRow(ResultSet resultSet, int i) throws SQLException {
                return TypeErrorModel.createFromResultSet(resultSet);
            }
        };
        logger.info(time + "Making list of joined columns");
        List<TypeErrorModel> joinedTables = mJdbcTemplateInstance.query(sql, mapper);
        logger.info(time + "Making list of joined columns result OK");
        return joinedTables;
    }
}

