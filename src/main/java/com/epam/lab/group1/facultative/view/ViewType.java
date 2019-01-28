package com.epam.lab.group1.facultative.view;

public enum ViewType {

    LOGIN("loginPage"),
    REGISTER("register"),
    COURSE("course"),
    COURSE_INFO("courseInfo"),
    COURSE_CREATE("createCourse"),
    COURSE_EDIT("editCourse"),
    ERROR("errorPage"),
    USER_STUDENT("student"),
    USER_TUTOR("tutor"),
    FEEDBACK("feedback");
    public String viewName;

    ViewType(String viewName) {
        this.viewName = viewName;
    }
}
