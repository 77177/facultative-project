package com.epam.lab.group1.facultative.exception.course.update;

public class CourseUpdateEmptyNameException extends CourseUpdateException {

    public CourseUpdateEmptyNameException() {
    }

    public CourseUpdateEmptyNameException(String s) {
        super(s);
    }

    public CourseUpdateEmptyNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseUpdateEmptyNameException(Throwable cause) {
        super(cause);
    }
}
