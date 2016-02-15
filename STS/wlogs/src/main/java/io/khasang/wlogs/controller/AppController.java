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
        DbModel db = new DbModel();
        String sql = "select * from tableTest";
        ArrayList<TestTableModel> testTableModelArrayList = TestTableModel.getListFromResultSet(db.getSelectResult(sql));
        if (testTableModelArrayList != null && db.getError() == null) {
            model.addAttribute("listTable", testTableModelArrayList);
        } else if (!db.getError().equals("")) {
            model.addAttribute("error", db.getError());
            db.setError(null);
        }
        return "table";
    }
}