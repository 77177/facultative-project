package com.epam.lab.group1.facultative.exception.course.update;

public class CourseUpdateException extends IllegalArgumentException {

    public CourseUpdateException() {
    }

    public CourseUpdateException(String s) {
        super(s);
    }

    public CourseUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseUpdateException(Throwable cause) {
        super(cause);
    }
}
