package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private CourseService courseService;
    private UserService userService;
    private FeedBackService feedBackService;

    public FeedBackController(CourseService courseService, UserService userService, FeedBackService feedBackService) {
        this.courseService = courseService;
        this.userService = userService;
        this.feedBackService = feedBackService;
    }

    @PostMapping(value = "/")
    public ModelAndView createFeedBack(@ModelAttribute(name = "feedback") FeedBack feedback) {
        ModelAndView modelAndView = new ModelAndView("feedBack");
        feedBackService.saveOrUpdate(feedback);
        modelAndView.addObject("feedback", feedBackService.getFeedBack(feedback.getCourseId(), feedback.getStudentId()));
        modelAndView.addObject("course", courseService.getById(feedback.getCourseId()).get());
        return modelAndView;
    }

    @GetMapping(value = "/user/{userId}/course/{courseId}")
    public ModelAndView feedBackPage(@PathVariable int userId, @PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView("feedBack");
        FeedBack feedBack = feedBackService.getFeedBack(courseId, userId);
        modelAndView.addObject("feedback", feedBack);
        modelAndView.addObject("course", courseService.getById(courseId).get());
        return modelAndView;
    }
}
