package io.khasang.wlogs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404Error(NoHandlerFoundException exception, HttpServletRequest request, Model model) {
        model.addAttribute("exceptionMessage", exception.getMessage());
        model.addAttribute("requestedUri", request.getRequestURI());
        model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
        return "error/wlogs_error";
    }

    @ExceptionHandler(Exception.class)
    public String handleError(final Throwable throwable, HttpServletRequest request, Model model) {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String exceptionMessage = getExceptionMessage(throwable, statusCode);
        String requestUri = request.getRequestURI();
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        model.addAttribute("exceptionMessage", exceptionMessage);
        model.addAttribute("requestedUri", requestUri);
        model.addAttribute("statusCode", statusCode);
        return "error/wlogs_error";
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
