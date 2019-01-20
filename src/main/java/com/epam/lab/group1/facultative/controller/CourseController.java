package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_INFO;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_CREATE;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_EDIT;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;
    private UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView(COURSE);
        modelAndView.addObject("courseList", courseService.findAll());
        return modelAndView;
    }

    @GetMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(COURSE_INFO);
        modelAndView.addObject("course", courseService.getById(courseId));
        modelAndView.addObject("studentList", userService.getAllStudentByCourseId(courseId));
        return modelAndView;
    }

    @GetMapping(value = "/action/create/{tutorId}")
    public ModelAndView createCourse(@PathVariable int tutorId) {
        ModelAndView modelAndView = new ModelAndView(COURSE_CREATE);
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
        ModelAndView modelAndView = new ModelAndView(COURSE_EDIT);
        modelAndView.addObject("tutorId", tutorId);
        modelAndView.addObject("course", courseService.getById(courseId));
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
                return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            }

            @Override
            public String print(LocalDate localDate, Locale locale) {
                return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
        }
        binder.addCustomFormatter(new LocalDateFormatter());
    }
}