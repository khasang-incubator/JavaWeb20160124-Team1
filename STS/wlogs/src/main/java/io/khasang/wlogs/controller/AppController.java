package io.khasang.wlogs.controller;

import io.khasang.wlogs.model.DbModel;
import io.khasang.wlogs.model.TestTableModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Controller
public class AppController {
    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to our best wLogs!");
        model.addAttribute("tagline", "The one and only amazing logs system!");
        return "welcome";
    }

    @RequestMapping("/backup")
    public String backup(Model model) {
        model.addAttribute("backup", "Success");
        return "backup";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("admin", "You are number 1!");
        return "admin";
    }

    @RequestMapping("/table")
    public String table(Model model) {
        model.addAttribute("table", "You have one table!");
        Statement statement = new DbModel().getStatement();
        String sql = "select * from tableTest";
        ResultSet resultSet = null;
        ArrayList<TestTableModel> testTableModelArrayList = null;
        try {
            if (statement != null) {
                resultSet = statement.executeQuery(sql);
            }
            else {
                model.addAttribute("error", "Ошибка: нет соединения с базой");
            }
            if (resultSet != null) {
                testTableModelArrayList = new ArrayList<TestTableModel>(resultSet.getFetchSize());
                while (resultSet.next()) {
                    TestTableModel testTableModel = new TestTableModel();
                    testTableModel.setServer(resultSet.getString(TestTableModel.tableFiled.server.toString()));
                    testTableModel.setComment(resultSet.getString(TestTableModel.tableFiled.comment.toString()));
                    testTableModel.setDate(resultSet.getString(TestTableModel.tableFiled.date.toString()));
                    testTableModel.setIssue(resultSet.getString(TestTableModel.tableFiled.issue.toString()));
                    testTableModelArrayList.add(testTableModel);
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            model.addAttribute("error", "Ошибка: " + e);
        }
        model.addAttribute("listTable", testTableModelArrayList);
        return "table";
    }
}