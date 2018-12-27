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
     * @return all student in db as list.
     */
    @GetMapping("")
    @ResponseBody
    public List<Student> getAll() {
        return Collections.emptyList();
    }

    /**
     * @param studentId
     * @return Student by db's student's id with corresponding courseList.
     */
    @GetMapping("/{studentId}")
    public ModelAndView getById(@PathVariable int studentId) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        return modelAndView;
    }

    /**
     * @param request post-request from user.
     * @return modelAndView with created by StudentService Student object with empty Course list.
     */
    @PostMapping("")
    public ModelAndView create(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        return modelAndView;
    }

    /**
     * @param studentId student to delete
     * @return redirect to /course if succeed, /student/studentId if not.
     */
    @DeleteMapping("")
    public String delete(@PathVariable int studentId) {
        if (false) {
            return "redirect:/course";
        } else {
            return "redirect:/student/" + studentId;
        }
    }
}