package io.khasang.wlogs.model;

public class TestTableModel {
    public enum tableFiled {server, date, issue, comment}

    private String server;
    private String date;
    private String issue;
    private String comment;

    public String getServer() {
        return server;
    }

    public String getDate() {
        return date;
    }

    public String getIssue() {
        return issue;
    }

    public String getComment() {
        return comment;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
