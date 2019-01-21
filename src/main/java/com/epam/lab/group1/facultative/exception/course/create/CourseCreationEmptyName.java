package com.epam.lab.group1.facultative.exception.course.create;

public class CourseCreationEmptyName extends CourseCreationException {

    public CourseCreationEmptyName() {
    }

    public CourseCreationEmptyName(String s) {
        super(s);
    }

    public CourseCreationEmptyName(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseCreationEmptyName(Throwable cause) {
        super(cause);
    }
}
