package com.epam.lab.group1.facultative.exception.course.create;

public class CourseCreationWrongDateException extends CourseCreationException {

    public CourseCreationWrongDateException() {
    }

    public CourseCreationWrongDateException(String s) {
        super(s);
    }

    public CourseCreationWrongDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseCreationWrongDateException(Throwable cause) {
        super(cause);
    }
}
