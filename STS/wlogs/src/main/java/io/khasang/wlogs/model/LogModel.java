package io.khasang.wlogs.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class LogModel {
    final public static String tableName = "wlogs";

    private int id;
    private Date occurredAt;
    private String errorLevel;
    private String errorSource;
    private String errorDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public static String getTableName() {
        return tableName;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public static LogModel createFromResultSet(ResultSet rs) throws SQLException {
        LogModel log = new LogModel();
        log.setId(rs.getInt("id"));
        log.setOccurredAt(rs.getDate("occurred_at"));
        log.setErrorLevel(rs.getString("error_level"));
        log.setErrorSource(rs.getString("error_source"));
        log.setErrorDescription(rs.getString("error_description"));
        return log;
    }
}
