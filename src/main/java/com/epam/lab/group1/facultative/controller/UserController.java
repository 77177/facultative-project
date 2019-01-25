package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import static com.epam.lab.group1.facultative.controller.ViewName.USER_STUDENT;
import static com.epam.lab.group1.facultative.controller.ViewName.USER_TUTOR;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;
import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserService userService;
    private CourseService courseService;

    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/profile")
    public ModelAndView sendRedirectToProfile(HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page) {
        logger.info("Caught request " + request.getRequestURL());
        int pageNumber = page == null ? 0 : page;
        ModelAndView modelAndView;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Fetch auth" + authentication);
        if (authentication != null && authentication.isAuthenticated()) {
            logger.info("user is Authenticated");
            SecurityContextUser principal = (SecurityContextUser) authentication.getPrincipal();
            modelAndView = userProfile(principal.getUserId(), pageNumber);
        } else {
            logger.info("user NOT is Authenticated");
            modelAndView = new ModelAndView(COURSE);
            logger.info("Set ModelAndView to Course" + modelAndView);
            modelAndView.addObject("courseList", courseService.findAll(pageNumber));
            logger.info("Adding Model " + modelAndView.getModel());
        }
        modelAndView.addObject("pageNumber", pageNumber);
        logger.info("Adding Model " + modelAndView.getModel());
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }

    @GetMapping("/{userId}/course/{courseId}/{action}")
    public String action(HttpServletRequest request, @PathVariable int userId, @PathVariable int courseId, @PathVariable String action) {
        logger.info("Caught request " + request.getRequestURL());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Fetch authentication" + authentication);
        if (authentication == null) {
            logger.info("Redirect to /course");
            return "redirect:/course";
        }
        SecurityContextUser principal = (SecurityContextUser) authentication.getPrincipal();
        logger.info("Fetch principal " + principal);
        if (principal == null) {
            logger.info("Redirect to /course");
            return "redirect:/course";
        }
        logger.warn("action may not exist");
        if (action != null) {
            logger.info("action exists");
            switch (action) {
                case "leave": {
                    logger.info("action=" + action);
                    principal.getCourseIdList().remove(new Integer(courseId));
                    userService.leaveCourse(userId, courseId);
                    break;
                }
                case "subscribe": {
                    logger.info("action=" + action);
                    principal.getCourseIdList().add(courseId);
                    userService.subscribeCourse(userId, courseId);
                    break;
                }
            }
        }
        logger.info("redirect to /course/" + courseId);
        return "redirect:/course/" + courseId;
    }

    private ModelAndView userProfile(int userId, int pageNumber) {
        User user = userService.getById(userId);
        ModelAndView modelAndView = new ModelAndView();
        if (user.getPosition().equals("student")) {
            logger.debug("User is a student.");
            modelAndView.setViewName(USER_STUDENT);
            logger.debug("ModelAndView set view: " + USER_STUDENT);
        } else if (user.getPosition().equals("tutor")) {
            logger.debug("User is a tutor.");
            modelAndView.setViewName(USER_TUTOR);
            logger.debug("ModelAndView set view: " + USER_TUTOR);
        }
        modelAndView.addObject("user", userService.getById(userId));
        logger.debug("ModelAndView set attribute user.");
        modelAndView.addObject("courseList", courseService.getAllByUserId(userId, pageNumber));
        logger.debug("ModelAndView set attribute courseList.");
        return modelAndView;
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView noResultExceptionHandler(NoResultException e) {
        logger.error("No such user in database. Message: " + e.getMessage());
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("exception_type", "no result in data base");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(PersistenceException.class)
    public ModelAndView persistingEntityExceptionHandler(Exception e) {
        logger.error("Persistence entity exception caught. Message: " + e.getMessage());
        ModelAndView modelAndView = new ModelAndView(ERROR);
        ErrorDto errorDto = new ErrorDto("PersistingEntityException in /user/** path", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }
}