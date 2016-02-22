package io.khasang.wlogs.model;

import java.sql.Date;

public class StatisticData extends LogsData {
    private long id;
    private Date time;
    private String issue;
    private String comment;

    public StatisticData() {}


    public StatisticData(int id, Date time, String issue, String comment) {
        this.id = id;
        this.time = time;
        this.issue = issue;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
