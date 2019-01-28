package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.view.builder.CourseCreateViewBuilder;
import com.epam.lab.group1.facultative.view.builder.CourseEditViewBuilder;
import com.epam.lab.group1.facultative.view.builder.CourseInfoViewBuilder;
import com.epam.lab.group1.facultative.view.builder.CourseViewBuilder;
import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.exception.CourseDoesNotExistException;
import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CourseService courseService;
    private UserService userService;

    @Autowired
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;

    @Autowired
    private CourseViewBuilder courseViewBuilder;

    @Autowired
    private CourseCreateViewBuilder courseCreateViewBuilder;

    @Autowired
    private CourseEditViewBuilder courseEditViewBuilder;

    @Autowired
    private CourseInfoViewBuilder courseInfoViewBuilder;

    @Autowired
    private SecurityContextUser securityContextUser;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView getAllCourses(@RequestParam(name = "page", required = false) Integer page) {
        int pageNumber = page == null ? 0 : page;
        return courseViewBuilder
            .setCourseList(courseService.findAll(pageNumber))
            .setPageNumber(pageNumber)
            .build();
    }

    @GetMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        //TODO add information about having feedback by a student on this course. Depend on existence of feedback
        // mark students with/without it.
        Course course = courseService.getById(courseId);
        return courseInfoViewBuilder
            .setCourse(course)
            .setTutorName(userService.getById(course.getTutorId()).getFullName())
            .setStudentList(userService.getAllStudentByCourseId(courseId))
            .build();
    }

    @GetMapping(value = "/action/create")
    public ModelAndView createCourse() {
        return courseCreateViewBuilder.build();
    }

    @PostMapping(value = "/action/create")
    public ModelAndView createCourse(@ModelAttribute Course course) {
        SingleCourseDto singleCourseDto = courseService.create(course);
        if (!singleCourseDto.isErrorPresent()) {
            return new ModelAndView(new RedirectView("/user/profile"));
        } else {
            return courseCreateViewBuilder.setErrorMessage(singleCourseDto.getErrorMessage()).build();
        }
    }

    @GetMapping(value = "/action/delete/{courseId}")
    public String deleteCourse(@PathVariable int courseId) {
        if (courseService.deleteById(courseId)) {
            return "redirect:/user/profile";
        } else {
            return "redirect:/course/" + courseId;
        }
    }

    @GetMapping(value = "/action/edit/{courseId}")
    public ModelAndView editCourse(@PathVariable int courseId) {
        if (securityContextUser.getUserId() == courseService.getById(courseId).getTutorId()) {
            return courseEditViewBuilder.setCourse(courseService.getById(courseId)).build();
        } else {
            return new ModelAndView(new RedirectView("/user/profile"));
        }
    }

    @PostMapping(value = "/action/edit")
    public ModelAndView editCourse(@ModelAttribute Course course) {
        SingleCourseDto singleCourseDto = courseService.update(course);
        if (!singleCourseDto.isErrorPresent()) {
            return new ModelAndView(new RedirectView("/course/" + course.getId()));
        } else {
            return courseEditViewBuilder
                .setCourse(course)
                .setErrorMessage(singleCourseDto.getErrorMessage())
                .build();
        }
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView noResultExceptionHandler(NoResultException e) {
        logger.error(e.getMessage(), e);
        return exceptionModelAndViewBuilder.setException(e).replaceUserMessage("No such course is here.").build();
    }

    @ExceptionHandler(CourseDoesNotExistException.class)
    public ModelAndView courseDoesNotExistException(CourseDoesNotExistException e) {
        logger.error(e.getMessage(), e);
        return exceptionModelAndViewBuilder.setException(e).replaceUserMessage("No such course is here.").build();
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