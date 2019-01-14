package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;
    private UserService userService;
    private final String courseView = "course";
    private final String courseInfoView = "courseInfo";
    private final String createCourseView = "createCourse";
    private final String editCourseView = "editCourse";

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @RequestMapping(value = "")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView(courseView);
        modelAndView.addObject("courseList", courseService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(courseInfoView);
        modelAndView.addObject("courseInfo", courseService.getById(courseId));
        modelAndView.addObject("studentList", userService.getAllByCourseId(courseId));
        return modelAndView;
    }

    @GetMapping(value = "action/createCourse")
    public ModelAndView createCourse() {
        ModelAndView modelAndView = new ModelAndView(createCourseView);
        return modelAndView;
    }

    @PostMapping(value = "action/createCourse")
    public ModelAndView createCourse(@ModelAttribute Course course) {
        ModelAndView modelAndView = new ModelAndView(courseInfoView);
        course.setTutorId(1);
        course.setActive(TRUE);
        modelAndView.addObject("courseInfo", courseService.create(course));
        modelAndView.addObject("studentList", userService.getAllByCourseId(1));
        return modelAndView;
    }

    @GetMapping(value = "action/delete/{courseId}")
    public ModelAndView deleteCourse(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(courseView);
        courseService.deleteById(courseId);
        modelAndView.addObject("courseList", courseService.getAll());
        return modelAndView;
    }

    @GetMapping(value = "action/editCourse/{courseId}")
    public ModelAndView editCourse() {
        ModelAndView modelAndView = new ModelAndView(editCourseView);
        return modelAndView;
    }

    @PostMapping(value = "action/editCourse/{courseId}")
    public ModelAndView editCourse(@ModelAttribute Course course, @RequestParam int tutorId, @PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(courseInfoView);
        course.setTutorId(tutorId);
        course.setCourseId(courseId);
        course.setStartingDate(LocalDate.of(1999,01,01));
        course.setFinishingDate(LocalDate.of(2000,01,01));
        course.setActive(TRUE);
        courseService.update(course);
        modelAndView.addObject("course", courseService.getById(courseId));
        modelAndView.addObject("studentList", userService.getAllByCourseId(1));
        return modelAndView;
    }
}