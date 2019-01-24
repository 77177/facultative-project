package com.epam.lab.group1.facultative.exception.course.create;

public class CourseCreationException extends  IllegalArgumentException {

    public CourseCreationException() {
    }

    public CourseCreationException(String s) {
        super(s);
    }

    public CourseCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseCreationException(Throwable cause) {
        super(cause);
    }
}
