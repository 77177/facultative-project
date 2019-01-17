package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.exception.external.EmailAlreadyExistsException;
import com.epam.lab.group1.facultative.exception.internal.UserWithEmailDoesNOtExistException;
import com.epam.lab.group1.facultative.exception.internal.UserWithIdDoesNotExistException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final String studentViewName = "user/student";
    private final String tutorViewName = "user/tutor";
    private final String errorViewName = "exception/exceptionPage";

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
                    userService.leaveCourse(userId, courseId);
                    break;
                }
                case "subscribe": {
                    userService.subscribeCourse(userId, courseId);
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
            if (principal.isStudent()) {
                modelAndView.addObject("courseList", courseService.getAllByUserId(principal.getUserId()));
                modelAndView.setViewName(studentViewName);
            } else {
                List<Course> all = courseService.getAllByTutorID(principal.getUserId());
                System.out.println(all.size());
                modelAndView.addObject("courseList", courseService.getAllByTutorID(principal.getUserId()));
                courseService.getAllByTutorID(principal.getUserId());
                modelAndView.setViewName(tutorViewName);
            }
        } else {
            modelAndView = new ModelAndView("course/course");
            modelAndView.addObject("courseList", courseService.getAll());
        }
        return modelAndView;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ModelAndView incorrectDataInputExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(errorViewName);
        ErrorDto errorDto = new ErrorDto("incorrect input", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }

    @ExceptionHandler(UserWithIdDoesNotExistException.class)
    public ModelAndView userByIdNotFoundExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(errorViewName);
        ErrorDto errorDto = new ErrorDto("user not found", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }

    @ExceptionHandler(UserWithEmailDoesNOtExistException.class)
    public ModelAndView userByEmailNotFoundExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(errorViewName);
        ErrorDto errorDto = new ErrorDto("user not found", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }
}
