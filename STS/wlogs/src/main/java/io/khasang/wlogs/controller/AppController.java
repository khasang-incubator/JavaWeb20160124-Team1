package io.khasang.wlogs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @RequestMapping("/hello")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to wLogs! Our Best Program!");
        model.addAttribute("tagline", "The one and only amazing logging system!");
        return "welcome";
    }

    @RequestMapping("/backup")
    public String backup(Model model) {
        model.addAttribute("backupstatus", "Backup Success");
        return "backup";
    }
}