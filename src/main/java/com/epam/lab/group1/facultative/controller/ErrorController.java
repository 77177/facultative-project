package com.epam.lab.group1.facultative.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

@Controller
public class ErrorController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping("/error")
    public ModelAndView error(HttpServletRequest request, Exception e) {
        logger.error("" +
                request.getAttribute("javax.servlet.error.status_code") +
                " " + request.getAttribute("javax.servlet.error.message") +
                " " + e.getClass(), e);
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("errorStatus", request.getAttribute("javax.servlet.error.status_code"));
        modelAndView.addObject("errorReason", request.getAttribute("javax.servlet.error.message"));
        return modelAndView;
    }
}
