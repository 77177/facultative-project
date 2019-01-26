package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.exception.CourseDoesNotExistException;
import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
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
import javax.servlet.http.HttpServletRequest;

import static com.epam.lab.group1.facultative.controller.ViewName.FEEDBACK;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserService userService;
    private CourseService courseService;
    private FeedBackService feedBackService;

    @Autowired
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;

    public FeedBackController(UserService userService, CourseService courseService, FeedBackService feedBackService) {
        this.userService = userService;
        this.courseService = courseService;
        this.feedBackService = feedBackService;
    }

    @PostMapping(value = "/")
    public ModelAndView createFeedBack(HttpServletRequest request, @ModelAttribute(name = "feedback") FeedBack feedback) {
        logger.info("Caught request " + request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView(FEEDBACK);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        feedBackService.saveOrUpdate(feedback);
        logger.info("SaveOrUpdate FeedBack " + feedback);
        modelAndView.addObject("feedback", feedBackService.getFeedBack(feedback.getCourseId(), feedback.getStudentId()));
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("student", userService.getById(feedback.getStudentId()));
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("course", courseService.getById(feedback.getCourseId()));
        logger.info("Adding Model " + modelAndView.getModel());
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }

    @GetMapping(value = "/user/{userId}/course/{courseId}")
    public ModelAndView getFeedbackPage(HttpServletRequest request, @PathVariable int userId, @PathVariable int courseId) {
        logger.info("Caught request " + request.getRequestURL());
        SecurityContextUser principal =
            (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Fetch principal" + principal);
        int principalUserId = principal.getUserId();
        ModelAndView modelAndView = new ModelAndView(FEEDBACK);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        FeedBack feedBack;
        if (principal.isStudent()) {
            logger.info("Principal is Student " + principal);
            feedBack = feedBackService.getFeedBack(courseId, principalUserId);
            logger.info("Fetch feedback " + feedBack);
        } else {
            logger.info("Principal is Not Student " + principal);
            feedBack = feedBackService.getFeedBack(courseId, userId);
            logger.info("Fetch feedback " + feedBack);
        }
        modelAndView.addObject("feedback", feedBack);
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("student", userService.getById(userId));
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("course", courseService.getById(courseId));
        logger.info("Adding Model " + modelAndView.getModel());
        return modelAndView;
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
}