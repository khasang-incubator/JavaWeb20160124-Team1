package io.khasang.wlogs.controller;

import io.khasang.wlogs.form.DeleteDataForm;
import io.khasang.wlogs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {
    @Autowired
    Login login;
    @Autowired
    Registration registration;
    @Autowired
    private LogManager logManager;
    @Autowired
    private ViewStatisticData viewStatisticData;
    @Autowired
    private ViewDataTable viewDataTable;
    @Autowired
    private Statistic statistic;
    @Autowired
    ViewDataFromTable viewDataFromTable;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private DataBaseHandler dbHandler;
    @Autowired
    private InsertComment insertComment;
    final public static Integer DEFAULT_LIMIT = 100;
    @Autowired
    @Qualifier("productorder")
    TableObjectInterface tableObjectInterface;

    @RequestMapping("/backup")
    //todo vlaptev  "mysqldump wlogs -u root -proot -r \"C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\backup.sql\"");
    public String backup(Model model) { //todo - select where backup to do, select table to backup
        model.addAttribute("backup", "Success");
        return "backup";
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
        model.addAttribute("welcome", ""); // todo main menu
        // todo add 8 button(6 blank, 1 with link to http://localhost:8080/, 2 with select like %event%
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

    @RequestMapping("login")
    //todo dalbot return user list from current logon name, db with id, username, role, description
    public String login(Model model) {
        model.addAttribute("users", login.showUsers());
        return "login";
    }

    @RequestMapping("/insert")
    public String insert(Model model) {
        model.addAttribute("tip", "Choose table to insert");
        return "insert";
    }

    @RequestMapping(value = "/showJoinedTables", method = RequestMethod.GET)
    public String performJoin(Model model,
                              @RequestParam(value = "selection", defaultValue = "-1") int[] tableNums) {
        if (tableNums[0] == -1) {
            return "/home";
        }
        model.addAttribute("joinedTbl", dbHandler.joinTables(tableNums));
        model.addAttribute("tableName1", dbHandler.getTableName(tableNums[0]));
        model.addAttribute("tableName2", dbHandler.getTableName(tableNums[1]));
        return "showJoinedTables";
    }

    @RequestMapping("/showtables")
    public String showwlogs(Model model) {
        model.addAttribute("wlogsContent", dbHandler.getWlogsTableContent());
        model.addAttribute("typeErrorContent", dbHandler.getTypeerrorTableContent());
        return "showtables";
    }

    @RequestMapping(value = "/createtblQuestion")
    public String createtblQuestion(Model model) {
        return "createtblsorlov";
    }


    @RequestMapping(value = "/createtablesorlov", method = RequestMethod.GET)
    public String createTable(Model model) {
        model.addAttribute("result", dbHandler.sqlInsertCheck());
        return "createtblsorlov";
    }

    @RequestMapping("/join")
    public String join(Model model) {
        model.addAttribute("tblOne", dbHandler.getTableName(0));
        model.addAttribute("tblTwo", dbHandler.getTableName(1));
        return "join";
    }

    <<<<<<<HEAD

    @RequestMapping("/registration") //todo dalbot
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addUserAction(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        String sqlAnswer = null;
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            sqlAnswer = registration.sqlInsert(username, password);
        } catch (Exception e) {
            sqlAnswer = "smth wrong";
        }
        model.addAttribute("formAnswer", sqlAnswer);
        return "registration";
    }

    @RequestMapping("/tempselect")
    public String selectData(Model model) {
        model.addAttribute("items", viewDataFromTable.selectWholeTable(tableObjectInterface));
        return "select";
    }
}