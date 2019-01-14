package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tutor")
public class TutorController {

    private UserService userService;
    private CourseService courseService;
    private final String viewName = "tutor";

    public TutorController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping(value = "/{tutorId}")
    public ModelAndView tutorProfile(@PathVariable int tutorId) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("courseList", courseService.getAllByUserId(tutorId));
        return modelAndView;
    }
}