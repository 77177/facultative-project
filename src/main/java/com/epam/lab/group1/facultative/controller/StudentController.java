package com.epam.lab.group1.facultative.controller;

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

    @RequestMapping(value = "get/{studentId}")
    public ModelAndView getById(@PathVariable int studentId) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("student", userService.getById(studentId));
        return modelAndView;
    }

    @RequestMapping(value = "getEmail/{studentId}/")
    public ModelAndView getById(@PathVariable Object studentId) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("student", userService.getByEmail((String) studentId));
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{studentId}")
    public ModelAndView deleteById(@PathVariable int studentId) {
        ModelAndView modelAndView = new ModelAndView("users");
        userService.deleteById(studentId);
        modelAndView.addObject("users", userService.getList());
        return modelAndView;
    }

    @RequestMapping(value = "/list")
    public ModelAndView getList() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getList());
        return modelAndView;
    }
}