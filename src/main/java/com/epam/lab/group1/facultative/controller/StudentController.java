package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Responsible for processing CRUD requests from users related with Student.
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private final String viewName = "student";

    /**
     * @param studentId
     * @return Student by db's student's id with corresponding courseList.
     */
    @RequestMapping(value = "/{studentId}")
    public ModelAndView getById(@PathVariable int studentId) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        return modelAndView;
    }
}