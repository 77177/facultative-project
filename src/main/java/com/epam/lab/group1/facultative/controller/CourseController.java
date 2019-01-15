package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.CourseDTO;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @GetMapping(value = "/action/create/{tutorId}")
    public ModelAndView createCourse(@PathVariable int tutorId) {
        ModelAndView modelAndView = new ModelAndView(createCourseView);
        modelAndView.addObject("tutorId", tutorId);
        return modelAndView;
    }

    @PostMapping(value = "/action/create")
    public void createCourse(@ModelAttribute Course course, @RequestParam int tutorId, HttpServletResponse response) {
        course.setActive(true);
        course.setTutorId(tutorId);
        courseService.create(course);
        try {
            response.sendRedirect("/user/profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/action/delete/{courseId}")
    public void deleteCourse(@PathVariable int courseId, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(courseView);
        courseService.deleteById(courseId);
        try {
            response.sendRedirect("/user/profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/{courseId}/action/edit/{tutorId}")
    public ModelAndView editCourse(@PathVariable int tutorId, @PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(editCourseView);
        List<Integer> listInt = new ArrayList<>();
        listInt.add(tutorId);
        listInt.add(courseId);
        modelAndView.addObject("listInt", listInt);
        return modelAndView;
    }

    @PostMapping(value = "/action/edit/")
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
}