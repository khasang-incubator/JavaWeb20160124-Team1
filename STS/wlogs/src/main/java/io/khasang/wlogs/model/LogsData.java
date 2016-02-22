package io.khasang.wlogs.model;

import java.sql.Date;

public class LogsData {
    //ID, minute, errorLvL
    private long ID;
    private Date minute;
    private String errorLvL;

    public LogsData() {};

    public LogsData(long ID, Date minute, String errorLvL) {
        this.ID = ID;
        this.minute = minute;
        this.errorLvL = errorLvL;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Date getMinute() {
        return minute;
    }

    public void setMinute(Date minute) {
        this.minute = minute;
    }

    public String getErrorLvL() {
        return errorLvL;
    }

    public void setErrorLvL(String errorLvL) {
        this.errorLvL = errorLvL;
    }
}
