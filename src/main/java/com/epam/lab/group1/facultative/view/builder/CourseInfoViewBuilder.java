package com.epam.lab.group1.facultative.view.builder;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static com.epam.lab.group1.facultative.view.ViewType.COURSE_INFO;

@Component
public class CourseInfoViewBuilder {

    private String tutorName = "";
    private Course course = new Course();
    private List<User> studentList = Collections.emptyList();

    public CourseInfoViewBuilder() {
    }

    public CourseInfoViewBuilder(String tutorName, Course course, List<User> studentList) {
        this.tutorName = tutorName;
        this.course = course;
        this.studentList = studentList;
    }

    public CourseInfoViewBuilder setTutorName(String tutorName) {
        this.tutorName = tutorName;
        return this;
    }

    public CourseInfoViewBuilder setCourse(Course course) {
        this.course = course;
        return this;
    }

    public CourseInfoViewBuilder setStudentList(List<User> studentList) {
        this.studentList = studentList;
        return this;
    }

    public ModelAndView build() {
        ModelAndView modelAndView = new ModelAndView(COURSE_INFO.viewName);
        modelAndView.addObject("tutorName", tutorName);
        modelAndView.addObject("course", course);
        modelAndView.addObject("studentList", studentList);
        return modelAndView;
    }
}