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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.epam.lab.group1.facultative.controller.ViewName.*;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CourseServiceInterface courseService;
    private UserServiceInterface userService;

    @Autowired
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView getAllCourses(@RequestParam(name = "page", required = false) Integer page) {
        int pageNumber = page == null ? 0 : page;
        ModelAndView modelAndView = new ModelAndView(COURSE);
        modelAndView.addObject("courseList", courseService.findAll(pageNumber));
        modelAndView.addObject("pageNumber", pageNumber);
        return modelAndView;
    }

    @GetMapping(value = "/{courseId}")
    public ModelAndView getById(@PathVariable int courseId) {
        //TODO add information about having feedback by a student on this course. Depend on existence of feedback
        // mark students with/without it.
        ModelAndView modelAndView = new ModelAndView(COURSE_INFO);
        Course course = courseService.getById(courseId);
        modelAndView.addObject("tutorName", userService.getById(course.getTutorId()).getFullName());
        modelAndView.addObject("course", course);
        modelAndView.addObject("studentList", userService.getAllStudentByCourseId(courseId));
        return modelAndView;
    }

    @GetMapping(value = "/action/create")
    public ModelAndView createCourse() {
        ModelAndView modelAndView = new ModelAndView(COURSE_CREATE);
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
            return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView();

        if (principal.getUserId() == courseService.getById(courseId).getTutorId()) {
            modelAndView.setViewName(COURSE_EDIT);
            modelAndView.addObject("course", courseService.getById(courseId));
            return modelAndView;
        } else {
            modelAndView.setView(new RedirectView("/user/profile"));
            return modelAndView;
        }
    }

    @PostMapping(value = "/action/edit")
    public ModelAndView editCourse(@ModelAttribute Course course) {
        if (course.getStartingDate() == null || course.getFinishingDate() == null) {
            throw new IllegalArgumentException("The one or both of the course dates are null");
        }
        SingleCourseDto singleCourseDto = courseService.update(course);
        ModelAndView modelAndView = new ModelAndView();
        if (!singleCourseDto.isErrorPresent()) {
            modelAndView.setView(new RedirectView("/course/" + course.getId()));
            return modelAndView;
        } else {
            modelAndView.setViewName(COURSE_EDIT);
            modelAndView.addObject("errorMessage", singleCourseDto.getErrorMessage());
            modelAndView.addObject("course", course);
            return modelAndView;
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