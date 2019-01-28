package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.view.builder.FeedbackViewBuilder;
import com.epam.lab.group1.facultative.exception.CourseDoesNotExistException;
import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.exception.NotTheCourseTutorException;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserService userService;
    private CourseService courseService;
    private FeedBackService feedBackService;
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;
    private FeedbackViewBuilder feedbackViewBuilder;

    @Autowired
    public FeedBackController(UserService userService, CourseService courseService, FeedBackService feedBackService,
                              ExceptionModelAndViewBuilder exceptionModelAndViewBuilder, FeedbackViewBuilder feedbackViewBuilder) {
        this.userService = userService;
        this.courseService = courseService;
        this.feedBackService = feedBackService;
        this.exceptionModelAndViewBuilder = exceptionModelAndViewBuilder;
        this.feedbackViewBuilder = feedbackViewBuilder;
    }

    @PostMapping(value = "/")
    public ModelAndView createFeedBack(@ModelAttribute(name = "feedback") FeedBack feedback) {
        feedBackService.saveOrUpdateFeedBack(feedback);
        return feedbackViewBuilder
            .setStudent(userService.getById(feedback.getStudentId()))
            .setCourse(courseService.getById(feedback.getCourseId()))
            .setFeedBack(feedBackService.getFeedBack(feedback.getCourseId(), feedback.getStudentId()))
            .build();
    }

    @GetMapping(value = "/user/{userId}/course/{courseId}")
    public ModelAndView getFeedbackPage(@PathVariable int userId, @PathVariable int courseId) {
        SecurityContextUser principal = (SecurityContextUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FeedBack feedBack;
        if (principal.isStudent()) {
            feedBack = feedBackService.getFeedBack(courseId, principal.getUserId());
        } else {
            feedBack = feedBackService.getFeedBack(courseId, userId);
        }
        return feedbackViewBuilder
            .setStudent(userService.getById(userId))
            .setCourse(courseService.getById(courseId))
            .setFeedBack(feedBack)
            .build();
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView noResultExceptionHandler(NoResultException e) {
        logger.error(e.getMessage(), e);
        return exceptionModelAndViewBuilder.setException(e).replaceUserMessage("No such course is here.").build();
    }

    @ExceptionHandler(CourseDoesNotExistException.class)
    public ModelAndView courseDoesNotExistException(CourseDoesNotExistException e) {
        logger.error(e.getMessage(), e);
        return exceptionModelAndViewBuilder.setException(e).replaceUserMessage("No such course is here.").build();
    }

    @ExceptionHandler(NotTheCourseTutorException.class)
    public ModelAndView notTheCourseTutorHandler(NotTheCourseTutorException e) {
        logger.error(e.getMessage(), e);
        return exceptionModelAndViewBuilder.setException(e).replaceUserMessage("No such course is here.").build();
    }
}