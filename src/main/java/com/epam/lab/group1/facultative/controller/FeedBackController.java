package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private CourseService courseService;
    private UserService userService;

    public FeedBackController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @RequestMapping("/")
    public ModelAndView feedBack(){
        List<Course> courseList = courseService.getAll();
        List<User> allStudents = userService.getAllStudents();
        ModelAndView modelAndView = new ModelAndView("feedBack");
        modelAndView.addObject("courses",courseList);
        modelAndView.addObject("students", allStudents);
        return modelAndView;
    }
}
