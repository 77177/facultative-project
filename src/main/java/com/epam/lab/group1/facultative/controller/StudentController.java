package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.epam.lab.group1.facultative.service.StudentService;
import com.epam.lab.group1.facultative.model.Course;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Responsible for processing CRUD requests from users related with Student.
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private final String viewName = "studentProfile";
    private StudentService studentService;
    private CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * @return all student in db as list.
     */
    @GetMapping("")
    @ResponseBody
    public List<Student> getAll() {
        return studentService.getAll();
    }

    /**
     * @param studentId
     * @return Student by db's student's id with corresponding courseList.
     */
    @GetMapping("/{studentId}")
    public ModelAndView getById(@PathVariable int studentId) {
        Student student = studentService.getById(studentId);
        List<Course> courseList = courseService.getByStudentId(studentId);
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("student", student);
        modelAndView.addObject("courseList", courseList);
        return modelAndView;
    }

    /**
     * @param request post-request from user.
     * @return modelAndView with created by StudentService Student object with empty Course list.
     */
    @PostMapping("")
    public ModelAndView create(HttpServletRequest request) {
        Student student = studentService.create(request);
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("student", student);
        modelAndView.addObject("courseList", Collections.EMPTY_LIST);
        return modelAndView;
    }

    /**
     * @param studentId student to delete
     * @return redirect to /course if succeed, /student/studentId if not.
     */
    public String delete(@PathVariable int studentId) {
        boolean isDeleted = studentService.getById(studentId);
        if (isDeleted) {
            return "redirect:/course";
        } else {
            return "redirect:/student/" + studentId;
        }
    }
}