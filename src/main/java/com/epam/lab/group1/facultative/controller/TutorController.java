package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tutor")
public class TutorController {
    private UserService userService;
    private final String viewName = "TutorProfile";

    public TutorController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{tutorId}")
    public ModelAndView getById(@PathVariable int tutorId) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("tutor", userService.getById(tutorId));
        return modelAndView;
    }
}