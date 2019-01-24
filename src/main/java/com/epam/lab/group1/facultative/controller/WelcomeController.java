package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import com.epam.lab.group1.facultative.service.CourseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;

import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

@Controller
public class WelcomeController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CourseService courseService;

    public WelcomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/**")
    public String welcome() {
        return "redirect:/course/";
    }

    @ExceptionHandler(PersistenceException.class)
    public ModelAndView persistingEntityExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(ERROR);
        ErrorDto errorDto = new ErrorDto("PersistingEntityException", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }
}