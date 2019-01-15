package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.CourseDTO;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "action/createCourse/{tutorId}")
    public ModelAndView createCourse(@PathVariable int tutorId) {
        ModelAndView modelAndView = new ModelAndView(createCourseView);
        modelAndView.addObject("tutorId", tutorId);
        return modelAndView;
    }

    @PostMapping(value = "action/createCourse")
    public void createCourse(@ModelAttribute CourseDTO courseDTO, @RequestParam int tutorId, HttpServletResponse response) {
        courseDTO.setTutorId(tutorId);
        courseDTO.setActive(TRUE);
        courseService.createCourseFromDto(courseDTO);
        try {
            response.sendRedirect("/user/profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "action/delete/{courseId}")
    public void deleteCourse(@PathVariable int courseId, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(courseView);
        courseService.deleteById(courseId);
        try {
            response.sendRedirect("/user/profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "{courseId}/action/editCourse/{tutorId}")
    public ModelAndView editCourse(@PathVariable int tutorId, @PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(editCourseView);
        List<Integer> listInt = new ArrayList<>();
        listInt.add(tutorId);
        listInt.add(courseId);
        modelAndView.addObject("listInt", listInt);
        return modelAndView;
    }

    @PostMapping(value = "action/editCourse/")
    public void editCourse(@ModelAttribute CourseDTO courseDTO, @RequestParam int tutorId, @RequestParam int courseId, HttpServletResponse response) {
        courseDTO.setTutorId(tutorId);
        courseDTO.setCourseId(courseId);
        courseDTO.setActive(courseService.isDateActive(courseService.courseDtoToCourse(courseDTO)));
        courseService.updateCourseFromDto(courseDTO);
        try {
            response.sendRedirect("/user/profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}