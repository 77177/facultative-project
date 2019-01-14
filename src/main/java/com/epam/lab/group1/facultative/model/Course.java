package com.epam.lab.group1.facultative.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class Course {

    private int courseId;
    private String courseName;
    private int tutorId;
    private LocalDate startingDate;
    private LocalDate finishingDate;
    private boolean active;
    //private List<User> usersList;

}
