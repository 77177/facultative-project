package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.view.builder.UserViewBuilder;
import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.model.UserPosition;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserService userService;
    private CourseService courseService;
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;
    private UserViewBuilder userViewBuilder;

    public UserController(UserService userService, CourseService courseService,
                          ExceptionModelAndViewBuilder exceptionModelAndViewBuilder, UserViewBuilder userViewBuilder) {
        this.userService = userService;
        this.courseService = courseService;
        this.exceptionModelAndViewBuilder = exceptionModelAndViewBuilder;
        this.userViewBuilder = userViewBuilder;
    }

    @RequestMapping("/profile")
    public ModelAndView sendRedirectToProfile(@RequestParam(name = "page", required = false) Integer page) {
        //TODO mark courses where the student has gotten feedback.
        int pageNumber = page == null ? 0 : page;
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getById(principal.getUserId());
        if (user.getPosition().equals("student")) {
            userViewBuilder.setPosition(UserPosition.STUDENT);
        } else if (user.getPosition().equals("tutor")) {
            userViewBuilder.setPosition(UserPosition.TUTOR);
        }
        return userViewBuilder
            .setUser(user)
            .setCourseList(courseService.getAllByUserId(user.getId(), pageNumber))
            .setPageNumber(pageNumber)
            .build();
    }

    @GetMapping("/{userId}/course/{courseId}/{action}")
    public String action(@PathVariable int userId, @PathVariable int courseId, @PathVariable String action) {
        //TODO check possibility of breaking the proceeding checks.
        SecurityContextUser principal = (SecurityContextUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @ExceptionHandler(NoResultException.class)
    public ModelAndView noResultExceptionHandler(NoResultException e) {
        logger.error(e.getMessage(), e);
        return exceptionModelAndViewBuilder.setException(e).replaceUserMessage("No such user is here.").build();
    }
}