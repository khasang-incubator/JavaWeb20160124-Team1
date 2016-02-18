package io.khasang.wlogs.controller;


import io.khasang.wlogs.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.khasang.wlogs.model.DbModel;
import io.khasang.wlogs.model.TestTableModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Controller
public class AppController {
    @RequestMapping("/backup")
    //todo vlaptev  "mysqldump wlogs -u root -proot -r \"C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\backup.sql\"");
    public String backup(Model model) { //todo - select where backup to do, select table to backup
        model.addAttribute("backup", "Success");
        return "backup";
    }

    final public static Integer DEFAULT_LIMIT = 100;
    @Autowired
    private LogManager logManager;
    @Autowired
    private LogRepository logRepository;

    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @RequestMapping(value = "/", name = "home")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("recordsTotal", logRepository.countAll());
        model.addAttribute("recordsPerPage", DEFAULT_LIMIT.toString());
        String offsetParam = request.getParameter("offset");
        Integer offset = 0;
        if (offsetParam != null) {
            model.addAttribute("currentOffset", offset = Integer.valueOf(offsetParam));
        } else {
            model.addAttribute("currentOffset", 0);
        }
        model.addAttribute("logs", logRepository.findAll(DEFAULT_LIMIT, offset));
        return "index";
    }

    @RequestMapping("/index")
    public String homePage() {
        return "forward:/";
    }

    @RequestMapping("/delete")
    public String deleteForm(Model model) {
        model.addAttribute("dateCriteriaHashMap", logManager.getAvailableDateCriteria());
        return "delete";
    }

    @RequestMapping("/shrink")
    public String shrink(Model model) {
        model.addAttribute("shrink", ""); // todo dzahar list of all tables at schema wlogs and select table to shrink
        return "shrink";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("welcome", ""); // todo main menu
        // todo add 8 button(6 blank, 1 with link to http://localhost:8080/, 2 with select like %event%
        return "welcome";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        try {
            String userUnderstandTermsParam = request.getParameter("understand_terms");
            Boolean userUnderstandTerms = userUnderstandTermsParam == null ? Boolean.FALSE : request.getParameter("understand_terms").equals("on");
            if (!userUnderstandTerms) {
                throw new RuntimeException("You have to read warning notice about this operation before proceed.");
            }
            Integer periodCriteriaId = Integer.valueOf(request.getParameter("period_criteria_id"));
            Integer affectedRows = logManager.delete(periodCriteriaId);
            redirectAttributes.addFlashAttribute("success", "Success deleted " + affectedRows);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:delete";
    }

    @RequestMapping("/import")
    public String importLogs(Model model) {
        return "import";
    }

    @RequestMapping("/export")
    public String exportLogs(Model model) {
        return "export";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("admin", "You are number 1!");
        return "admin";
    }

    @RequestMapping("/showlogin") //todo ashishkin  select all error description with like %user%
    public String showlogin(Model model) {
        model.addAttribute("showlogin", "You are number 1!");
        return "showlogin";
    }

    @RequestMapping("/createtable")
    //todo vbaranov create table "statistic" with column "server" = id, "date", "issue" = description, "comment"
    public String crateTable(Model model) {
        Statistic statistic = new Statistic();
        statistic.createTable();
        statistic.clearTable();
        statistic.insertDataToTable();
        model.addAttribute("createtable", statistic.getStatistic());
        return "createtable";
    }

    @RequestMapping("/insertcomment")
    public String insertcomment(Model model) {
        model.addAttribute("insertcomment", ""); //todo szador insert comment to table "statistic", select date description
        return "insertcomment";
    }

    @RequestMapping("/tableview")
    public String tableView(Model model) {
        ViewDataTable viewDataTable = new ViewDataTable();
        model.addAttribute("tableview", viewDataTable.outData());
        return "tableview";
    }

    @RequestMapping("login") //todo dalbot
    public String login(Model model) {
        model.addAttribute("login", "Login Users"); //return user list from current logon name, db with id, username, role, description
        return "login";
    }

    @RequestMapping("join") //todo sorlov
    public String join(Model model) {
        model.addAttribute("join", "Login Users"); //join with error_level and return type of critical
        return "join";
    }

    @RequestMapping("registration") //todo dalbot
    public String registration() {
        return "registration";
    }

    @RequestMapping("/table")
    public String table(Model model) {
        model.addAttribute("table", "You have one table!");
        DbModel db = new DbModel();
        String sql = "select * from wlogs";
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