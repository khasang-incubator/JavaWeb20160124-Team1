package io.khasang.wlogs.controller;

import io.khasang.wlogs.model.InsertDataTable;
import io.khasang.wlogs.model.LogManager;
import io.khasang.wlogs.model.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import io.khasang.wlogs.model.ViewDataTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {
    @RequestMapping("/backup")
    public String backup(Model model) {
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

    @RequestMapping("/createtable")
    public String crateTable(Model model) {
        InsertDataTable sql = new InsertDataTable();
        model.addAttribute("createtable", sql.sqlInsertCheck());
        return "createtable";
    }

    @RequestMapping("/tableview")
    public String tableView(Model model) {
        ViewDataTable viewDataTable = new ViewDataTable();
        model.addAttribute("tableview", viewDataTable.outData());
        return "tableview";
    }

    @RequestMapping("/insert")
    public String insert(Model model) {
        model.addAttribute("tip", "Choose table to insert");
        return "insert";
    }

    @RequestMapping("/insert")
    public String insert(Model model) {
        model.addAttribute("tip", "Choose table to insert");
        return "insert";
    }
}