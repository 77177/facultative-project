package com.epam.lab.group1.facultative.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @RequestMapping("/**")
    public String welcome() {
        return "redirect:/course/";
    }
}