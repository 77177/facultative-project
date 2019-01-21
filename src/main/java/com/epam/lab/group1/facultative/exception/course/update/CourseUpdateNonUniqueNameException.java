package com.epam.lab.group1.facultative.exception.course.update;

public class CourseUpdateNonUniqueNameException extends CourseUpdateException {

    public CourseUpdateNonUniqueNameException() {
    }

    public CourseUpdateNonUniqueNameException(String s) {
        super(s);
    }

    public CourseUpdateNonUniqueNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseUpdateNonUniqueNameException(Throwable cause) {
        super(cause);
    }
}
