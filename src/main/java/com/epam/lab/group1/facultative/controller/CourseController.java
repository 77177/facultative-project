package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.exception.external.CourseTitleAlreadyExistsException;
import com.epam.lab.group1.facultative.exception.internal.CourseWithIdDoesNotExistException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;
    private UserService userService;
    private final String courseView = "course/course";
    private final String courseInfoView = "course/courseInfo";
    private final String createCourseView = "course/createCourse";
    private final String editCourseView = "course/editCourse";
    private final String errorViewName = "exception/exceptionPage";

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView(courseView);
        modelAndView.addObject("courseList", courseService.getAll());
        return modelAndView;
    }

    @GetMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(courseInfoView);
        Course course = courseService.getById(courseId);
        User tutor = userService.getById(course.getTutorId());
        if (!tutor.getPosition().equals("tutor")) {
            throw new RuntimeException("user is not a tutor");
        }
        modelAndView.addObject("course", course);
        modelAndView.addObject("tutor", tutor);
        modelAndView.addObject("studentList", userService.getAllStudentByCourseId(courseId));
        return modelAndView;
    }

    @GetMapping(value = "/action/create/{tutorId}")
    public ModelAndView createCourse(@PathVariable int tutorId) {
        ModelAndView modelAndView = new ModelAndView(createCourseView);
        modelAndView.addObject("tutorId", tutorId);
        return modelAndView;
    }

    @PostMapping(value = "/action/create")
    public String createCourse(@ModelAttribute Course course) {
        courseService.create(course);
        return "redirect:/user/profile";
    }

    @GetMapping(value = "/action/delete/{courseId}")
    public String deleteCourse(@PathVariable int courseId) {
        courseService.deleteById(courseId);
        return "redirect:/user/profile";
    }

    @GetMapping(value = "/{courseId}/action/edit/{tutorId}")
    public ModelAndView editCourse(@PathVariable int tutorId, @PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(editCourseView);
        modelAndView.addObject("tutorId", tutorId);
        modelAndView.addObject("courseId", courseId);
        return modelAndView;
    }

    @PostMapping(value = "/action/edit")
    public String editCourse(@ModelAttribute Course course) {
        courseService.update(course);
        return "redirect:/user/profile";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        class LocalDateFormatter implements Formatter<LocalDate> {

            @Override
            public LocalDate parse(String date, Locale locale) {
                LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                return localDate;
            }

            @Override
            public String print(LocalDate localDate, Locale locale) {
                return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
        }
        binder.addCustomFormatter(new LocalDateFormatter());
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView sqlExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(errorViewName);
        ErrorDto errorDto = new ErrorDto("SqlException", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }

    @ExceptionHandler(CourseTitleAlreadyExistsException.class)
    public ModelAndView incorrectDataInputExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(errorViewName);
        ErrorDto errorDto = new ErrorDto("incorrect input", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }

    @ExceptionHandler(CourseWithIdDoesNotExistException.class)
    public ModelAndView courseNotFoundExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(errorViewName);
        ErrorDto errorDto = new ErrorDto("course not found", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }
}