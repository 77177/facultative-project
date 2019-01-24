package com.epam.lab.group1.facultative.exception.course.update;

public class CourseUpdateCourseWithIdDoesNotExistException extends CourseUpdateException {

    public CourseUpdateCourseWithIdDoesNotExistException() {
    }

    public CourseUpdateCourseWithIdDoesNotExistException(String s) {
        super(s);
    }

    public CourseUpdateCourseWithIdDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseUpdateCourseWithIdDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
