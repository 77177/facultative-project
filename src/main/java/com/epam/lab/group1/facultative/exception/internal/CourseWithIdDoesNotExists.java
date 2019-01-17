package com.epam.lab.group1.facultative.exception.internal;

public class CourseWithIdDoesNotExists extends EntityNotFound {

    public CourseWithIdDoesNotExists() {
    }

    public CourseWithIdDoesNotExists(String message) {
        super(message);
    }

    public CourseWithIdDoesNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseWithIdDoesNotExists(Throwable cause) {
        super(cause);
    }

    public CourseWithIdDoesNotExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
