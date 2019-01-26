package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;

@ControllerAdvice(basePackages = "com.epam.lab.group1.facultative.controller")
public class ExceptionControllerAdvice {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        logger.error("ArgumentTypeMismatchException encountered. Message: " + e.getMessage());
        return exceptionModelAndViewBuilder
            .setException(e)
            .replaceUserMessage("Wrong path argument in path.")
            .build();
    }

    @ExceptionHandler(PersistenceException.class)
    public ModelAndView persistenceExceptionHandler(PersistenceException e) {
        logger.error("Persistence Exception Encountered. Message: " + e.getMessage());
        return exceptionModelAndViewBuilder
            .setException(e)
            .replaceUserMessage("Database issues.")
            .build();
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        logger.error("Exception encountered. Message: " + e.getMessage());
        return exceptionModelAndViewBuilder
            .setException(e)
            .replaceUserMessage("Something very bad happen.")
            .build();
    }
}
