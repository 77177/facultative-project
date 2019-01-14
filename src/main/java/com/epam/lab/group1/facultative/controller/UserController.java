package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
        ModelAndView modelAndView = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            SecurityContextUser principal = (SecurityContextUser)authentication.getPrincipal();
            if (principal.isStudent()) {
                modelAndView = studentProfile(principal.getUserId());
            } else {
                modelAndView = tutorProfile(principal.getUserId());
            }
        } else {
            modelAndView = new ModelAndView("course");
            modelAndView.addObject("courseList", courseService.getAll());
        }
        return modelAndView;
    }

    public ModelAndView studentProfile(int studentId) {
        ModelAndView modelAndView = new ModelAndView(studentViewName);
        modelAndView.addObject("student", userService.getById(studentId).orElse(new User()));
        modelAndView.addObject("courseList", courseService.getAllByUserId(studentId));
        return modelAndView;
    }

    public ModelAndView tutorProfile(int tutorId) {
        ModelAndView modelAndView = new ModelAndView(tutorViewName);
        modelAndView.addObject("courseList", courseService.getAllByUserId(tutorId));
        modelAndView.addObject("tutor", userService.getById(tutorId));
        return modelAndView;
    }
}
