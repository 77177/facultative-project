package com.epam.lab.group1.facultative.exception.internal;

public class CourseWithTitleDoesNotExistException extends EntityNotFoundException {

    public CourseWithTitleDoesNotExistException() {
    }

    public CourseWithTitleDoesNotExistException(String message) {
        super(message);
    }

    public CourseWithTitleDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseWithTitleDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public CourseWithTitleDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
