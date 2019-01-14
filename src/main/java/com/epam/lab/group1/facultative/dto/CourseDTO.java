package com.epam.lab.group1.facultative.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CourseDTO {
    private int courseId;
    private String courseName;
    private int tutorId;
    private String startingDate;
    private String finishingDate;
    private boolean active;
}
