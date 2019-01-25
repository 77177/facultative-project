package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.dto.ErrorDto;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;

import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

@ControllerAdvice(basePackages = "com.epam.lab.group1.facultative.controller")
public class ExceptionControllerAdvice {

    private final Logger logger = Logger.getLogger(this.getClass());

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        logger.error("ArgumentTypeMismatchException encountered. Message: " + e.getMessage());
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("exception_type", e.getClass().getSimpleName());
        modelAndView.addObject("message", e.getMessage());
        ErrorDto errorDto = new ErrorDto("ArgumentTypeMismatchException in /course/** path", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        logger.error("Exception encountered. Message: " + e.getMessage());
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject("exception_type", e.getClass().getSimpleName());
        modelAndView.addObject("message", e.getMessage());
        ErrorDto errorDto = new ErrorDto("Exception in /course/** path", e.getMessage());
        modelAndView.addObject("error", errorDto);
        return modelAndView;
    }
}
