package com.epam.lab.group1.facultative.view.builder;

import com.epam.lab.group1.facultative.model.Course;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.lab.group1.facultative.view.ViewType.COURSE_EDIT;

@Component
public class CourseEditViewBuilder {

    private Course course = new Course();
    private String errorMessage = null;

    public CourseEditViewBuilder() {
    }

    public CourseEditViewBuilder(Course course, String errorMessage) {
        this.course = course;
        this.errorMessage = errorMessage;
    }

    public CourseEditViewBuilder setCourse(Course course) {
        this.course = course;
        return this;
    }

    public CourseEditViewBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ModelAndView build() {
        ModelAndView modelAndView = new ModelAndView(COURSE_EDIT.viewName);
        modelAndView.addObject("course", course);
        modelAndView.addObject("errorMessage", errorMessage);
        errorMessage = null;
        return modelAndView;
    }
}
