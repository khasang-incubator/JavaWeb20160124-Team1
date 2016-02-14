package io.khasang.wlogs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to our best wLogs!");
        model.addAttribute("tagline", "The one and only amazing logs system!");
        return "welcome";
    }

    @RequestMapping("/anotherlogin")
    public String welcomeHW(Model model) {
        model.addAttribute("greeting", "Welcome to our best wLogs!");
        model.addAttribute("status", "Does user exist?");
        model.addAttribute("tagline", "The one and only amazing logs system!");
        return "welcomeHW";
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
}