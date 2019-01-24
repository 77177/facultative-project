package com.epam.lab.group1.facultative.exception.course.update;

public class CourseUpdateWrongDateException extends CourseUpdateException {

    public CourseUpdateWrongDateException() {
    }

    public CourseUpdateWrongDateException(String s) {
        super(s);
    }

    public CourseUpdateWrongDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseUpdateWrongDateException(Throwable cause) {
        super(cause);
    }
}
