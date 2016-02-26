package io.khasang.wlogs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Controller
public class SecurityController {
    @RequestMapping("/login")
    public String login() {
        return "wlogs_login";
    }
}
