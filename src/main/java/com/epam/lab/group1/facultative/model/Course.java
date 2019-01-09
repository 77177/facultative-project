package com.epam.lab.group1.facultative.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Course {
    private int courseId;
    private String courseName;
    private String tutorId;
    private Date startingDate;
    private Date finishingDate;
    private boolean active;
    private List<Student> studentsList;

}
