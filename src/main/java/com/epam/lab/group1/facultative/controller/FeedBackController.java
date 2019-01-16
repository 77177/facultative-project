package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private CourseService courseService;
    private UserService userService;

    public FeedBackController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping(value = "/")
    public ModelAndView createFeedBack(@ModelAttribute FeedBack feedback){
        ModelAndView modelAndView = new ModelAndView("feedBack");
        //TODO save feedback to the database
        return modelAndView;
    }

    @GetMapping(value = "/user/{userId}/course/{courseId}")
    public ModelAndView feedBackPage(@PathVariable int userId,@PathVariable int courseId){
        ModelAndView modelAndView = new ModelAndView("feedBack");
        //TODO get feedback from the database
        FeedBack feedBack = new FeedBack();
        feedBack.setText("feedback");
        modelAndView.addObject("feedback", feedBack.getText());
        modelAndView.addObject("courseId", courseId);
        return modelAndView;
    }
}
