package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
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
    public ModelAndView getAllCourses(HttpServletRequest request) {
        logger.info("Caught request " + request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView(COURSE);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        modelAndView.addObject("courseList", courseService.findAll());
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