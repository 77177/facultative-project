package com.epam.lab.group1.facultative.view.builder;

import com.epam.lab.group1.facultative.model.Course;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static com.epam.lab.group1.facultative.view.ViewType.COURSE;

@Component
public class CourseViewBuilder {

    private int pageNumber;
    private List<Course> courseList = Collections.emptyList();

    public CourseViewBuilder() {
    }

    public CourseViewBuilder(int pageNumber, List<Course> courseList) {
        this.pageNumber = pageNumber;
        this.courseList = courseList;
    }

    public CourseViewBuilder setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public CourseViewBuilder setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        return this;
    }

    public ModelAndView build() {
        ModelAndView modelAndView = new ModelAndView(COURSE.viewName);
        modelAndView.addObject("courseList", courseList);
        modelAndView.addObject("pageNumber", pageNumber);
        return modelAndView;
    }
}
