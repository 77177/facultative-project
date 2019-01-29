package com.epam.lab.group1.facultative.view.builder;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.lab.group1.facultative.view.ViewType.COURSE_CREATE;

@Component
public class CourseCreateViewBuilder {

    private String errorMessage;

    public CourseCreateViewBuilder() {
    }

    public CourseCreateViewBuilder(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public CourseCreateViewBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ModelAndView build() {
        ModelAndView modelAndView = new ModelAndView(COURSE_CREATE.viewName);
        modelAndView.addObject("errorMessage", errorMessage);
        errorMessage = null;
        return modelAndView;
    }
}
