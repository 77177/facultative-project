package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_INFO;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_CREATE;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_EDIT;
import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

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
    public ModelAndView getAllCourses(@RequestParam(name = "page", required = false) int page) {
        ModelAndView modelAndView = new ModelAndView(COURSE);
        modelAndView.addObject("courseList", courseService.findAll(page));
        modelAndView.addObject("pageNumber", page);
        return modelAndView;
    }

    @GetMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView(COURSE_INFO);
        Course course = courseService.getById(courseId);
        modelAndView.addObject("tutorName", userService.getById(course.getTutorId()).getFullName());
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

    @PostMapping(value = "/action/create")
    public ModelAndView createCourse(@ModelAttribute Course course) {
        SingleCourseDto singleCourseDto = courseService.create(course);
        ModelAndView modelAndView = new ModelAndView();
        if (!singleCourseDto.isErrorPresent()) {
            modelAndView.setView(new RedirectView("/user/profile"));
            return modelAndView;
        } else {
            modelAndView.setViewName(COURSE_CREATE);
            modelAndView.addObject("errorMessage", singleCourseDto.getErrorMessage());
            modelAndView.addObject("tutorId", course.getTutorId());
            return modelAndView;
        }
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
    public ModelAndView editCourse(@ModelAttribute Course course) {
        SingleCourseDto singleCourseDto = courseService.update(course);
        ModelAndView modelAndView = new ModelAndView();
        if (!singleCourseDto.isErrorPresent()) {
            modelAndView.setView(new RedirectView("/user/profile"));
            return modelAndView;
        } else {
            modelAndView.setViewName(COURSE_EDIT);
            modelAndView.addObject("errorMessage", singleCourseDto.getErrorMessage());
            modelAndView.addObject("tutorId", singleCourseDto.getCourse().getTutorId());
            modelAndView.addObject("course", course);
            return modelAndView;
        }
    }

    @ExceptionHandler(PersistenceException.class)
    public ModelAndView persistenceExceptionHandler(PersistenceException e) {
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("exception_type", "db exception");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
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