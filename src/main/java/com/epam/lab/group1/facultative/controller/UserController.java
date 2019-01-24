package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;

import static com.epam.lab.group1.facultative.controller.ViewName.USER_STUDENT;
import static com.epam.lab.group1.facultative.controller.ViewName.USER_TUTOR;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;
import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserService userService;
    private CourseService courseService;

    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/profile")
    public ModelAndView sendRedirectToProfile(@RequestParam(name = "page", required = false) int page) {
        ModelAndView modelAndView;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextUser principal = (SecurityContextUser) authentication.getPrincipal();
            if (principal.isStudent()) {
                modelAndView = studentProfile(principal.getUserId(), page);
            } else {
                modelAndView = tutorProfile(principal.getUserId(), page);
            }
        } else {
            modelAndView = new ModelAndView(COURSE);
            modelAndView.addObject("courseList", courseService.findAll(page));
            modelAndView.addObject("pageNumber", page);
        }
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

    private ModelAndView studentProfile(int studentId, int pageNumber) {
        ModelAndView modelAndView = new ModelAndView(USER_STUDENT);
        modelAndView.addObject("user", userService.getById(studentId));
        modelAndView.addObject("courseList", courseService.getAllByUserId(studentId, pageNumber));
        return modelAndView;
    }

    private ModelAndView tutorProfile(int tutorId, int pageNumber) {
        ModelAndView modelAndView = new ModelAndView(USER_TUTOR);
        modelAndView.addObject("user", userService.getById(tutorId));
        modelAndView.addObject("courseList", courseService.getAllByUserId(tutorId, pageNumber));
        return modelAndView;
    }

    @ExceptionHandler(PersistenceException.class)
    public ModelAndView persistingEntityExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(ERROR);
        ErrorDto errorDto = new ErrorDto("PersistingEntityException", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }
}