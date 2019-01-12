package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    private CourseService courseService;

    public WelcomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/**")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView("courseList");
        modelAndView.addObject("", courseService.getAll());
        return modelAndView;
    }
}
