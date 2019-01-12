package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;
    private final String viewName = "course";
    private final String viewNameCourseInfo = "courseInfo";
    private final String viewNameCreateCourse = "createCourse";

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("list", courseService.getList());
        return modelAndView;
    }

    @RequestMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(viewNameCourseInfo);
        modelAndView.addObject("courseInfo", courseService.getById(courseId));
        modelAndView.addObject("list", courseService.getStudentList(courseId));
        return modelAndView;
    }

    @GetMapping(value = "/createCourse")
    public ModelAndView CreateCourse() {
        ModelAndView modelAndView = new ModelAndView(viewNameCreateCourse);
        return modelAndView;
    }

    @PostMapping(value = "/createCourse")
    public ModelAndView CreateNewCourse() {
        //TODO uses methdod courseService.create()
        ModelAndView modelAndView = new ModelAndView(viewNameCourseInfo);
        return modelAndView;
    }
}