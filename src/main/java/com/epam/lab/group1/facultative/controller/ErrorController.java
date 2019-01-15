package com.epam.lab.group1.facultative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    private final String errorView = "errorPage";

    @RequestMapping("/error")
    public ModelAndView error(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(errorView);
        modelAndView.addObject("errorStatus", request.getAttribute("javax.servlet.error.status_code") );
        modelAndView.addObject("errorReason", request.getAttribute("javax.servlet.error.message"));
        return modelAndView;
    }

}
