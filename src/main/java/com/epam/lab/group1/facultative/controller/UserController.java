package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.lab.group1.facultative.controller.ViewName.STUDENT;
import static com.epam.lab.group1.facultative.controller.ViewName.TUTOR;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;

@Controller
@RequestMapping("/user")
public class UserController {

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
            SecurityContextUser principal = (SecurityContextUser) authentication.getPrincipal();
            if (principal.isStudent()) {
                modelAndView = studentProfile(principal.getUserId());
            } else {
                modelAndView = tutorProfile(principal.getUserId());
            }
        } else {
            modelAndView = new ModelAndView(COURSE);
            modelAndView.addObject("courseList", courseService.findAll());
        }
        return modelAndView;
    }

    @GetMapping("/{userId}/course/{courseId}/{action}")
    public String action(@PathVariable int userId, @PathVariable int courseId,
                               @PathVariable String action) {
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
        return "redirect:/profile";
    }

    private ModelAndView studentProfile(int studentId) {
        ModelAndView modelAndView = new ModelAndView(STUDENT);
        modelAndView.addObject("user", userService.getById(studentId).orElse(new User()));
        modelAndView.addObject("courseList", courseService.getAllById(studentId));
        return modelAndView;
    }

    private ModelAndView tutorProfile(int tutorId) {
        ModelAndView modelAndView = new ModelAndView(TUTOR);
        modelAndView.addObject("user", userService.getById(tutorId));
        modelAndView.addObject("courseList", courseService.getAllById(tutorId));
        return modelAndView;
    }
}