package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.lab.group1.facultative.controller.ViewName.FEEDBACK;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CourseService courseService;
    private FeedBackService feedBackService;

    public FeedBackController(CourseService courseService, FeedBackService feedBackService) {
        this.courseService = courseService;
        this.feedBackService = feedBackService;
    }

    @PostMapping(value = "/")
    public ModelAndView createFeedBack(@ModelAttribute(name = "feedback") FeedBack feedback) {
        ModelAndView modelAndView = new ModelAndView(FEEDBACK);
        feedBackService.saveOrUpdate(feedback);
        modelAndView.addObject("feedback", feedBackService.getFeedBack(feedback.getCourseId(), feedback.getStudentId()));
        modelAndView.addObject("course", courseService.getById(feedback.getCourseId()).orElse(new Course()));
        return modelAndView;
    }

    @GetMapping(value = "/user/{userId}/course/{courseId}")
    public ModelAndView getFeedbackPage(@PathVariable int userId, @PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(FEEDBACK);
        FeedBack feedBack = feedBackService.getFeedBack(courseId, userId);
        modelAndView.addObject("feedback", feedBack);
        modelAndView.addObject("course", courseService.getById(courseId).orElse(new Course()));
        return modelAndView;
    }
}