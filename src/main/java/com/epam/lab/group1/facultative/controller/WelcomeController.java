package com.epam.lab.group1.facultative.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class WelcomeController {

    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView("course");
        return modelAndView;
    }
}
