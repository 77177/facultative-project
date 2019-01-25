package com.epam.lab.group1.facultative.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class WelcomeController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping("/**")
    public String welcome(HttpServletRequest request) {
        logger.info("Caught request " + request.getRequestURL());
        logger.info("Send redirect to /course/");
        return "redirect:/course/";
    }
}