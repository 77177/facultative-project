package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.exception.CourseDoesNotExistException;
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

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
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
    public ModelAndView getAllCourses(HttpServletRequest request,@RequestParam(name = "page", required = false) Integer page) {
        logger.info("Caught request " + request.getRequestURL());
        int pageNumber = page == null ? 0 : page;
        ModelAndView modelAndView = new ModelAndView(COURSE);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        modelAndView.addObject("courseList", courseService.findAll(pageNumber));
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("pageNumber", pageNumber);
        logger.info("Adding Model " + modelAndView.getModel());
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }

    @GetMapping(value = "/{courseId}")
    public ModelAndView getById(HttpServletRequest request, @PathVariable int courseId) {

        logger.info("Caught request " + request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView(COURSE_INFO);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        Course course = courseService.getById(courseId);
        logger.info("Creating Course " + course);
        modelAndView.addObject("tutorName", userService.getById(course.getTutorId()).getFullName());
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("course", course);
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("studentList", userService.getAllStudentByCourseId(courseId));
        logger.info("Adding Model " + modelAndView.getModel());
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }

    @GetMapping(value = "/action/create/{tutorId}")
    public ModelAndView createCourse(HttpServletRequest request, @PathVariable int tutorId) {
        logger.info("Caught request " + request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView(COURSE_CREATE);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        modelAndView.addObject("tutorId", tutorId);
        logger.info("Adding Model " + modelAndView.getModel());
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }

    @PostMapping(value = "/action/create")
    public ModelAndView createCourse(HttpServletRequest request, @ModelAttribute Course course) {
        logger.info("Caught request " + request.getRequestURL());
        SingleCourseDto singleCourseDto = courseService.create(course);
        logger.info("Create " + singleCourseDto);
        ModelAndView modelAndView = new ModelAndView();
        logger.info("Create ModelAndView");
        if (!singleCourseDto.isErrorPresent()) {
            modelAndView.setView(new RedirectView("/user/profile"));
            logger.info("Set View " + modelAndView.getViewName());
            logger.info("Send Model to " + modelAndView.getViewName());
            return modelAndView;
        } else {
            modelAndView.setViewName(COURSE_CREATE);
            logger.info("Set View " + modelAndView.getViewName());
            modelAndView.addObject("errorMessage", singleCourseDto.getErrorMessage());
            logger.info("Adding Model " + modelAndView.getModel());
            modelAndView.addObject("tutorId", course.getTutorId());
            logger.info("Adding Model " + modelAndView.getModel());
            logger.info("Send Model to " + modelAndView.getViewName());
            return modelAndView;
        }
    }

    @GetMapping(value = "/action/delete/{courseId}")
    public String deleteCourse(HttpServletRequest request, @PathVariable int courseId) {
        logger.info("Caught request " + request.getRequestURL());
        courseService.deleteById(courseId);
        logger.info("Deleted course with id=" + courseId);
        logger.info("Send redirect to /user/profile");
        return "redirect:/user/profile";
    }

    @GetMapping(value = "/{courseId}/action/edit/{tutorId}")
    public ModelAndView editCourse(HttpServletRequest request, @PathVariable int tutorId, @PathVariable int courseId) {
        logger.info("Caught request " + request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView(COURSE_EDIT);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        modelAndView.addObject("tutorId", tutorId);
        logger.info("Adding Model " + modelAndView.getModel());
        modelAndView.addObject("course", courseService.getById(courseId));
        logger.info("Adding Model " + modelAndView.getModel());
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }

    @PostMapping(value = "/action/edit")
    public ModelAndView editCourse(HttpServletRequest request, @ModelAttribute Course course) {
        //TODO Could come null dates. Check necessary!
        logger.info("Caught request " + request.getRequestURL());
        SingleCourseDto singleCourseDto = courseService.update(course);
        logger.info("Update " + course);
        ModelAndView modelAndView = new ModelAndView();
        logger.info("Create ModelAndView");
        if (!singleCourseDto.isErrorPresent()) {
            logger.info("No Error is present in " + singleCourseDto);
            modelAndView.setView(new RedirectView("/user/profile"));
            logger.info("Send redirect to" + modelAndView.getView());
            return modelAndView;
        } else {
            logger.warn("Error potentially present in " + singleCourseDto);
            modelAndView.setViewName(COURSE_EDIT);
            logger.info("Set View " + modelAndView.getViewName());
            modelAndView.addObject("errorMessage", singleCourseDto.getErrorMessage());
            logger.info("Adding Model " + modelAndView.getModel());
            modelAndView.addObject("tutorId", singleCourseDto.getCourse().getTutorId());
            logger.info("Adding Model " + modelAndView.getModel());
            modelAndView.addObject("course", course);
            logger.info("Adding Model " + modelAndView.getModel());
            logger.info("Send Model " + modelAndView.getViewName());
            return modelAndView;
        }
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView noResultExceptionHandler(NoResultException e) {
        logger.error("No such course in database. Message: " + e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("exception_type", "no result in data base");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(CourseDoesNotExistException.class)
    public ModelAndView courseDoesNotExistException(CourseDoesNotExistException e) {
        logger.error(e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("exception_type", e.getClass().getSimpleName());
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(PersistenceException.class)
    public ModelAndView persistenceExceptionHandler(PersistenceException e) {
        logger.error("Persistence Exception Encountered. Message: " + e.getMessage());
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("exception_type", "db exception");
        modelAndView.addObject("message", e.getMessage());
        ErrorDto errorDto = new ErrorDto("PersistingEntityException in /course/** path", e.getMessage());
        modelAndView.addObject("error", errorDto);
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