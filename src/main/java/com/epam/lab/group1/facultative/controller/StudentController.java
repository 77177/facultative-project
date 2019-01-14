package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {

    private UserService userService;
    private final String viewName = "student";

    public StudentController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{studentId}")
    public ModelAndView getById(@PathVariable int studentId) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("student", userService.getById(studentId).orElse(new User()));
        return modelAndView;
    }

}