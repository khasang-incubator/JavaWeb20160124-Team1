package io.khasang.wlogs.controller;

import io.khasang.wlogs.model.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/ajax")
@Controller
public class AjaxController {
    @Autowired
    private LogManager logManager;

    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @RequestMapping(value = "/import-fixtures", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String loadFixtures(HttpServletResponse response) {
        String responseBody = "";
        try {
            logManager.loadFixtures();
            responseBody = "{\"status\":\"OK\"}";
        } catch (Exception e) {
            responseBody = "{\"status\":\"error\", \"error\":\"" + e.getMessage() + "\"}";
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return responseBody;
    }

    @RequestMapping(value = "/create-table", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String createTable(HttpServletResponse response) {
        String responseBody = "";
        try {
            logManager.createTable();
            responseBody = "{\"status\":\"OK\"}";
        } catch (Exception e) {
            responseBody = "{\"status\":\"error\", \"error\":\"" + e.getMessage() + "\"}";
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return responseBody;
    }
}
