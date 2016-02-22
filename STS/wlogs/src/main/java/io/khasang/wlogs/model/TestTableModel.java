package io.khasang.wlogs.model;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TestTableModel {
    /*
        имена полей в таблице в БД
     */
    public enum tableField {server, date, issue, comment}

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

    public static ArrayList<TestTableModel> getListFromResultSet(ResultSet resultSet) {
        ArrayList<TestTableModel> testTableModelArrayList = null;
        try {
            if (resultSet != null){
                testTableModelArrayList = new ArrayList<TestTableModel>(resultSet.getFetchSize());
                while (resultSet.next()) {
                    TestTableModel testTableModel = new TestTableModel();
                    testTableModel.setServer(resultSet.getString(TestTableModel.tableField.server.toString()));
                    testTableModel.setComment(resultSet.getString(TestTableModel.tableField.comment.toString()));
                    testTableModel.setDate(resultSet.getString(TestTableModel.tableField.date.toString()));
                    testTableModel.setIssue(resultSet.getString(TestTableModel.tableField.issue.toString()));
                    testTableModelArrayList.add(testTableModel);
                }
            }
        }
        catch (Exception e){
            return null;
        }
        return testTableModelArrayList;
    }
}
