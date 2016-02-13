package io.khasang.wlogs.controller;

import io.khasang.wlogs.model.LogManager;
import io.khasang.wlogs.model.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {
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

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
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
}