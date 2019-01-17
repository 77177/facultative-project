package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final String studentViewName = "student";
    private final String tutorViewName = "tutor";

    private UserService userService;
    private CourseService courseService;

    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/profile")
    public ModelAndView sendRedirectToProfile() {
        return formProfile();
    }

    @RequestMapping("/{userId}/course/{courseId}")
    public ModelAndView action(@PathVariable int userId, @PathVariable int courseId,
                               @RequestParam(name = "action", required = false) String action) {
        if (action != null) {
            switch (action) {
                case "leave": {
                    //TODO userService.leaveCourse(userId, courseId)
                    break;
                }
                case "subscribe": {
                    //TODO userService.subscribeCourse(userId, courseId)
                    break;
                }
            }
        }
        return formProfile();
    }

    private ModelAndView formProfile() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextUser principal = (SecurityContextUser) authentication.getPrincipal();
            modelAndView.addObject("user", userService.getById(principal.getUserId()));
            modelAndView.addObject("courseList", courseService.getAllByUserId(principal.getUserId()));
            if (principal.isStudent()) {
                modelAndView.setViewName(studentViewName);
            } else {
                modelAndView.setViewName(tutorViewName);
            }
        } else {
            modelAndView = new ModelAndView("course/course");
            modelAndView.addObject("courseList", courseService.getAll());
        }
        return modelAndView;
    }
}
