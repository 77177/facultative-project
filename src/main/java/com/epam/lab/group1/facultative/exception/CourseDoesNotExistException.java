package com.epam.lab.group1.facultative.exception;

public class CourseDoesNotExistException extends IllegalArgumentException {

    public CourseDoesNotExistException() {
    }

    public CourseDoesNotExistException(String s) {
        super(s);
    }

    public CourseDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
