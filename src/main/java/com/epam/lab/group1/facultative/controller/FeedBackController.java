package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
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

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ModelAndView feedBack(@ModelAttribute(name = "feedback") FeedBack feedback){
        ModelAndView modelAndView = new ModelAndView("feedBack");
        //TODO save feedback to the database
        return modelAndView;
    }

    @RequestMapping(value = "/{studentId}/{courseId}")
    public ModelAndView feedBack(@PathVariable int studentId,@PathVariable int courseId){
        ModelAndView modelAndView = new ModelAndView("feedBack");
        //TODO get feedback from the database
        FeedBack feedBack = new FeedBack();
        feedBack.setString("feedback");
        modelAndView.addObject("feedback", feedBack);
        return modelAndView;
    }
}
