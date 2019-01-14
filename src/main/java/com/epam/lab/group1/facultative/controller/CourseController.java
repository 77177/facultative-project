package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.CourseDTO;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public void createCourse(@ModelAttribute CourseDTO courseDTO, HttpServletResponse response) {
        courseDTO.setTutorId(1);
        courseDTO.setActive(TRUE);
        courseService.createCourseFromDto(courseDTO);
        try {
            response.sendRedirect("/course");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "action/delete/{courseId}")
    public void deleteCourse(@PathVariable int courseId, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(courseView);
        courseService.deleteById(courseId);
        try {
            response.sendRedirect("/course");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "action/editCourse")
    public ModelAndView editCourse() {
        ModelAndView modelAndView = new ModelAndView(editCourseView);
        return modelAndView;
    }

    @PostMapping(value = "action/editCourse")
    public void editCourse(@ModelAttribute CourseDTO courseDTO, @RequestParam int tutorId, @RequestParam int courseId, HttpServletResponse response) {
        courseDTO.setTutorId(tutorId);
        courseDTO.setCourseId(courseId);
        courseDTO.setActive(TRUE);
        courseService.updateCourseFromDto(courseDTO);
        try {
            response.sendRedirect("/tutor/" + tutorId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}