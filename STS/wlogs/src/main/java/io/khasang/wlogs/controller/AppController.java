package io.khasang.wlogs.controller;

import io.khasang.wlogs.form.DeleteDataForm;
import io.khasang.wlogs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class AppController {
    @Autowired
    private LogManager logManager;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private InsertDataTable insertDataTable;
    @Autowired
    private InsertComment insertComment;
    @Autowired
    private ViewStatisticData viewStatisticData;
    @Autowired
    private ViewDataTable viewDataTable;
    @Autowired
    private Statistic statistic;
    @Autowired
    DeleteDataTable deleteDataTable;

    final public static Integer DEFAULT_LIMIT = 100;

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
            try {
                offset = Integer.valueOf(offsetParam);
            } catch (NumberFormatException e) {
                offset = 0;
            }
            model.addAttribute("currentOffset", offset);
        } else {
            model.addAttribute("currentOffset", 0);
        }
        String filterParam = request.getParameter("filter");
        if (filterParam != null) {
            model.addAttribute("currentFilter", filterParam);
        } else {
            model.addAttribute("currentFilter", "");
        }
        model.addAttribute("logs", logRepository.findAll(DEFAULT_LIMIT, offset));
        return "index";
    }

    @RequestMapping("/index")
    public String homePage() {
        return "forward:/";
    }

    @RequestMapping("/shrink")
    public String shrink(Model model) {
        model.addAttribute("shrink", ""); // todo dzahar list of all tables at schema wlogs and select table to shrink
        return "shrink";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("welcome", ""); // todo main menu aushar
        // todo add 8 button(6 blank, 1 with link to http://localhost:8080/, 2 with select like %event%
        // todo view import export delete shink admin createtable help logout insertcomment join
        return "welcome";
    }

    @RequestMapping("/delete")
    public String deleteForm(Model model) {
        model.addAttribute("errorSources", this.logRepository.getErrorSources());
        model.addAttribute("errorLevels", this.logRepository.getErrorLevels());
        model.addAttribute("deleteDataForm", new DeleteDataForm());
        model.addAttribute("logRecordsTotal", logRepository.countAll());
        return "delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(@ModelAttribute DeleteDataForm deleteDataForm, HttpServletRequest request,
                               RedirectAttributes redirectAttributes, Model model) {
        try {
            Integer affectedRows = logManager.delete(deleteDataForm);
            redirectAttributes.addFlashAttribute("success", "Success! Count of deleted rows: " + affectedRows);
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

    @RequestMapping("/showlogin") //todo ashishkin select all error description with like %user%
    public String showlogin(Model model) {
        model.addAttribute("showlogin", "You are number 1!");
        return "showlogin";
    }

    @RequestMapping("/createtable")
    //todo vbaranov create table "statistic" with column "server" = id, "date", "issue" = description, "comment"
    public String crateTable(Model model) {

        //Statistic statistic = new Statistic();
        statistic.createTable();
        statistic.clearTable();
        statistic.insertDataToTable();
        model.addAttribute("createtable", statistic.getStatistic());

        return "createtable";
    }

    @RequestMapping("/insertcomment")
    public String insertComment(Model model) {
        model.addAttribute("showstatisticdata", viewStatisticData.showStatisticData()); //todo szador insert comment to table "statistic", select date description
        model.addAttribute("insertcomment", insertComment.sqlInsertCheck());
        return "insertcomment";
    }

    @RequestMapping("/tableview")
    public String tableView(Model model) {
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

    @RequestMapping("/insert")
    public String insert(Model model) {
        model.addAttribute("tip", "Choose table to insert");
        return "insert";
    }

    @RequestMapping("/backup")
    //todo vlaptev  "mysqldump wlogs -u root -proot -r \"C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\backup.sql\"");
    public String backup(Model model) { //todo - select where backup to do, select table to backup
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