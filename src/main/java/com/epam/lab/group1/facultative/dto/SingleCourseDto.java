package com.epam.lab.group1.facultative.dto;

import com.epam.lab.group1.facultative.model.Course;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@Component
public class SingleCourseDto {

    private boolean isErrorPresent;
    private Course course;
    private String errorMessage;

    public boolean isErrorPresent() {
        return isErrorPresent;
    }

    public void setErrorPresent(boolean errorPresent) {
        isErrorPresent = errorPresent;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
