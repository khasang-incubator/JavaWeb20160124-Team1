package io.khasang.wlogs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorController {
    @RequestMapping("404")
    public String error404(HttpServletRequest request, HttpServletResponse response, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        model.addAttribute("exceptionMessage", exceptionMessage);
        model.addAttribute("requestedUri", requestUri);
        model.addAttribute("statusCode", statusCode);
        return "error/404";
    }

    private String getExceptionMessage(final Throwable throwable, final Integer statusCode) {
        if (throwable != null) {
            Throwable previous = throwable.getCause();
            String message = "";
            while (null != previous) {
                message = previous.getMessage();
                previous = previous.getCause();
            }
            return message;
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }
}
