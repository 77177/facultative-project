package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.User;
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
        ModelAndView modelAndView = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextUser principal = (SecurityContextUser)authentication.getPrincipal();
            if (principal.isStudent()) {
                modelAndView = studentProfile(principal.getUserId());
            } else {
                modelAndView = tutorProfile(principal.getUserId());
            }
        } else {
            modelAndView = new ModelAndView("course");
            modelAndView.addObject("courseList", courseService.findAll());
        }
        return modelAndView;
    }

    @RequestMapping("/{userId}/course/{courseId}/{action}/")
    public ModelAndView action(@PathVariable int userId, @PathVariable int courseId,
                               @PathVariable String action) {
        ModelAndView modelAndView = null;
        if (action != null) {
            switch (action) {
                case "leave": {
                    userService.leaveCourse(userId, courseId);
                    break;
                }
                case "subscribe": {
                    userService.subscribeCourse(userId, courseId);
                    break;
                }
            }
        } else {
            modelAndView = new ModelAndView("course");
        }
        return modelAndView;
    }

    private ModelAndView studentProfile(int studentId) {
        ModelAndView modelAndView = new ModelAndView(studentViewName);
        modelAndView.addObject("user", userService.getById(studentId).orElse(new User()));
        modelAndView.addObject("courseList", courseService.getAllById(studentId));
        return modelAndView;
    }

    private ModelAndView tutorProfile(int tutorId) {
        ModelAndView modelAndView = new ModelAndView(tutorViewName);
        modelAndView.addObject("user", userService.getById(tutorId));
        modelAndView.addObject("courseList", courseService.getAllById(tutorId));
        return modelAndView;
    }
}
