package com.epam.lab.group1.facultative.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;
import static com.epam.lab.group1.facultative.controller.ViewName.ACCESS_DENIED;

@Controller
public class ErrorController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping("/error")
    public ModelAndView error(HttpServletRequest request, Exception e) {
        String s = "javax.servlet.error.";
        logger.error(request.getAttribute(s + "status_code") + " " +
                request.getAttribute(s + "message") + " " +
                request.getAttribute(s + "exception_type") + " " +
                request.getAttribute(s + "exception") + " " +
                request.getAttribute(s + "request_uri"));
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("errorStatus", request.getAttribute(s + "status_code"));
        modelAndView.addObject("message", request.getAttribute(s + "message"));
        modelAndView.addObject("exception_type", request.getAttribute(s + "exception_type"));
        modelAndView.addObject("exception", request.getAttribute(s + "exception"));
        modelAndView.addObject("request_uri", request.getAttribute(s + "request_uri"));
        return modelAndView;
    }

    @RequestMapping("/access-denied")
    public ModelAndView accessDenied(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView(ACCESS_DENIED);
        return modelAndView;
    }
}
