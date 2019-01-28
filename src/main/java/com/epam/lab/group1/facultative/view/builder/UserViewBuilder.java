package com.epam.lab.group1.facultative.view.builder;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.model.UserPosition;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static com.epam.lab.group1.facultative.view.ViewType.USER_STUDENT;
import static com.epam.lab.group1.facultative.view.ViewType.USER_TUTOR;

@Component
public class UserViewBuilder {

    private int pageNumber = 0;
    private User user = new User();
    private UserPosition userPosition;
    private List<Course> courseList = Collections.emptyList();

    public UserViewBuilder() {
    }

    public UserViewBuilder(int pageNumber, User user, UserPosition userPosition, List<Course> courseList) {
        this.pageNumber = pageNumber;
        this.user = user;
        this.userPosition = userPosition;
        this.courseList = courseList;
    }

    public UserViewBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public UserViewBuilder setPosition(UserPosition userPosition) {
        this.userPosition = userPosition;
        return this;
    }

    public UserViewBuilder setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public UserViewBuilder setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        return this;
    }

    public ModelAndView build() {
        ModelAndView modelAndView = new ModelAndView();
        if (userPosition.equals(UserPosition.STUDENT)) {
            modelAndView.setViewName(USER_STUDENT.viewName);
        } else {
            modelAndView.setViewName(USER_TUTOR.viewName);
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("pageNumber", pageNumber);
        modelAndView.addObject("courseList", courseList);
        return modelAndView;
    }
}
