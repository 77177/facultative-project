package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.exception.course.create.CourseCreationException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private final Logger logger = Logger.getLogger(this.getClass());
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
        Course course = courseService.getById(courseId);
        modelAndView.addObject("tutorName", userService.getById(course.getId()).getFullName());
        modelAndView.addObject("course", course);
        modelAndView.addObject("studentList", userService.getAllStudentByCourseId(courseId));
        return modelAndView;
    }

    @GetMapping(value = "/action/create/{tutorId}")
    public ModelAndView createCourse(@PathVariable int tutorId) {
        ModelAndView modelAndView = new ModelAndView(COURSE_CREATE);
        modelAndView.addObject("tutorId", tutorId);
        return modelAndView;
    }
    /////////////////////////////////////////////////////////////////////HERE
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

    @ExceptionHandler(CourseCreationException.class)
    public ModelAndView persistingEntityExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(COURSE_CREATE);
        ErrorDto errorDto = new ErrorDto("PersistingEntityException", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }

}