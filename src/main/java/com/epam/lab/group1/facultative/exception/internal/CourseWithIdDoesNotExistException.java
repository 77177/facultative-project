package com.epam.lab.group1.facultative.exception.internal;

public class CourseWithIdDoesNotExistException extends EntityNotFoundException {

    public CourseWithIdDoesNotExistException() {
    }

    public CourseWithIdDoesNotExistException(String message) {
        super(message);
    }

    public CourseWithIdDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseWithIdDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public CourseWithIdDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
