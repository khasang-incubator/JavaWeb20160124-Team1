package io.khasang.wlogs.model;

import org.w3c.dom.Text;

import java.sql.Timestamp;

public class StatisticModel {
    public enum tableField {server, date, issue, comment}
    private String server;
    private Timestamp date;
    private String issue;
    private String comment;

    public void setServer(String server) {
        this.server = server;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getServer() {
        return server;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getIssue() {
        return issue;
    }

    public String getComment() {
        return comment;
    }
}
