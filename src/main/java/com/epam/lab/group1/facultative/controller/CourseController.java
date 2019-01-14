package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;
    private UserService userService;
    private final String viewName = "course";
    private final String viewNameCourseInfo = "courseInfo";
    private final String viewNameCreateCourse = "createCourse";

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @RequestMapping(value = "")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("courseList", courseService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(viewNameCourseInfo);
        modelAndView.addObject("courseInfo", courseService.getById(courseId));
        modelAndView.addObject("studentList", userService.getAllByCourseId(courseId));
        return modelAndView;
    }

    @GetMapping(value = "/createCourse")
    public ModelAndView CreateCourse() {
        ModelAndView modelAndView = new ModelAndView(viewNameCreateCourse);
        return modelAndView;
    }

    @PostMapping(value = "/createCourse")
    public ModelAndView CreateNewCourse(@ModelAttribute Course course) {
        ModelAndView modelAndView = new ModelAndView(viewNameCourseInfo);
        course.setTutorId(5);
        course.setActive(TRUE);
        modelAndView.addObject("courseInfo", courseService.create(course));
        modelAndView.addObject("studentList", userService.getAllByCourseId(1));
        return modelAndView;

    }
}