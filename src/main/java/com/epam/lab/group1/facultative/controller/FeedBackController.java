package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;

import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;
import static com.epam.lab.group1.facultative.controller.ViewName.FEEDBACK;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserService userService;
    private CourseService courseService;
    private FeedBackService feedBackService;

    public FeedBackController(UserService userService, CourseService courseService, FeedBackService feedBackService) {
        this.userService = userService;
        this.courseService = courseService;
        this.feedBackService = feedBackService;
    }

    @PostMapping(value = "/")
    public ModelAndView createFeedBack(@ModelAttribute(name = "feedback") FeedBack feedback) {
        ModelAndView modelAndView = new ModelAndView(FEEDBACK);
        feedBackService.saveOrUpdate(feedback);
        modelAndView.addObject("feedback", feedBackService.getFeedBack(feedback.getCourseId(), feedback.getStudentId()));
        modelAndView.addObject("student", userService.getById(feedback.getStudentId()));
        modelAndView.addObject("course", courseService.getById(feedback.getCourseId()));
        return modelAndView;
    }

    @GetMapping(value = "/user/{userId}/course/{courseId}")
    public ModelAndView getFeedbackPage(@PathVariable int userId, @PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(FEEDBACK);
        FeedBack feedBack = feedBackService.getFeedBack(courseId, userId);
        modelAndView.addObject("feedback", feedBack);
        modelAndView.addObject("student", userService.getById(userId));
        modelAndView.addObject("course", courseService.getById(courseId));
        return modelAndView;
    }

    @ExceptionHandler(PersistenceException.class)
    public ModelAndView sqlExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(ERROR);
        ErrorDto errorDto = new ErrorDto("PersistingEntityException", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }
}