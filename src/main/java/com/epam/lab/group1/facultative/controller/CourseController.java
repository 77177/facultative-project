package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.exception.CourseDoesNotExistException;
import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.CourseServiceInterface;
import com.epam.lab.group1.facultative.service.UserService;
import com.epam.lab.group1.facultative.service.UserServiceInterface;
import com.epam.lab.group1.facultative.view.builder.CourseCreateViewBuilder;
import com.epam.lab.group1.facultative.view.builder.CourseEditViewBuilder;
import com.epam.lab.group1.facultative.view.builder.CourseInfoViewBuilder;
import com.epam.lab.group1.facultative.view.builder.CourseViewBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private CourseServiceInterface courseService;
    private UserServiceInterface userService;
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;
    private CourseViewBuilder courseViewBuilder;
    private CourseCreateViewBuilder courseCreateViewBuilder;
    private CourseEditViewBuilder courseEditViewBuilder;
    private CourseInfoViewBuilder courseInfoViewBuilder;
    private LocaleHolder localeHolder;

    @Autowired
    public CourseController(CourseService courseService, UserService userService,
                            ExceptionModelAndViewBuilder exceptionModelAndViewBuilder, CourseViewBuilder courseViewBuilder,
                            CourseCreateViewBuilder courseCreateViewBuilder, CourseEditViewBuilder courseEditViewBuilder,
                            CourseInfoViewBuilder courseInfoViewBuilder, LocaleHolder localeHolder) {
        this.courseService = courseService;
        this.userService = userService;
        this.exceptionModelAndViewBuilder = exceptionModelAndViewBuilder;
        this.courseViewBuilder = courseViewBuilder;
        this.courseCreateViewBuilder = courseCreateViewBuilder;
        this.courseEditViewBuilder = courseEditViewBuilder;
        this.courseInfoViewBuilder = courseInfoViewBuilder;
        this.localeHolder = localeHolder;
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
    public ModelAndView createCourse(@ModelAttribute Course course, Locale locale) {
        localeHolder.setLocale(locale);
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
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getUserId() == courseService.getById(courseId).getTutorId()) {
            return courseEditViewBuilder.setCourse(courseService.getById(courseId)).build();
        } else {
            return new ModelAndView(new RedirectView("/user/profile"));
        }
    }

    @PostMapping(value = "/action/edit")
    public ModelAndView editCourse(@ModelAttribute Course course, Locale locale) {
        localeHolder.setLocale(locale);
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