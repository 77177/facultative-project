package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;

@Controller
public class WelcomeController {

    private CourseService courseService;

    public WelcomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/**")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView(COURSE);
        modelAndView.addObject("courseList", courseService.findAll());
        return modelAndView;
    }
}