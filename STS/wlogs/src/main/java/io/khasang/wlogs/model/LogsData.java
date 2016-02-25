package io.khasang.wlogs.model;

public class LogsData {
    //ID, minute, errorLvL
    private long ID;
    private String minute;
    private String errorLvL;

    public LogsData() {};

    public LogsData(long ID, String minute, String errorLvL) {
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

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getErrorLvL() {
        return errorLvL;
    }

    public void setErrorLvL(String errorLvL) {
        this.errorLvL = errorLvL;
    }
}
