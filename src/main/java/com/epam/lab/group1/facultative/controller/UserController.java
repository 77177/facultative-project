package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.CourseServiceInterface;
import com.epam.lab.group1.facultative.service.UserService;
import com.epam.lab.group1.facultative.service.UserServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import static com.epam.lab.group1.facultative.controller.ViewName.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserServiceInterface userService;
    private CourseServiceInterface courseService;

    @Autowired
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;

    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/profile")
    public ModelAndView sendRedirectToProfile(@RequestParam(name = "page", required = false) Integer page) {
        int pageNumber = page == null ? 0 : page;
        ModelAndView modelAndView;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextUser principal = (SecurityContextUser) authentication.getPrincipal();
            modelAndView = userProfile(principal.getUserId(), pageNumber);
        } else {
            modelAndView = new ModelAndView(COURSE);
            modelAndView.addObject("courseList", courseService.findAll(pageNumber));
        }
        modelAndView.addObject("pageNumber", pageNumber);
        return modelAndView;
    }

    @GetMapping("/{userId}/course/{courseId}/{action}")
    public String action(@PathVariable int userId, @PathVariable int courseId, @PathVariable String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "redirect:/course";
        }
        SecurityContextUser principal = (SecurityContextUser) authentication.getPrincipal();
        if (principal == null) {
            return "redirect:/course";
        }
        if (action != null) {
            switch (action) {
                case "leave": {
                    principal.getCourseIdList().remove(new Integer(courseId));
                    userService.leaveCourse(userId, courseId);
                    break;
                }
                case "subscribe": {
                    principal.getCourseIdList().add(courseId);
                    userService.subscribeCourse(userId, courseId);
                    break;
                }
            }
        }
        return "redirect:/course/" + courseId;
    }

    private ModelAndView userProfile(int userId, int pageNumber) {
        //TODO mark courses where the student has gotten feedback.
        User user = userService.getById(userId);
        ModelAndView modelAndView = new ModelAndView();
        if (user.getPosition().equals("student")) {
            modelAndView.setViewName(USER_STUDENT);
        } else if (user.getPosition().equals("tutor")) {
            modelAndView.setViewName(USER_TUTOR);
        }
        modelAndView.addObject("user", userService.getById(userId));
        modelAndView.addObject("courseList", courseService.getAllByUserId(userId, pageNumber));
        return modelAndView;
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView noResultExceptionHandler(NoResultException e) {
        logger.error(e.getMessage(), e);
        return exceptionModelAndViewBuilder.setException(e).replaceUserMessage("No such user is here.").build();
    }
}