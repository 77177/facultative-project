package com.epam.lab.group1.facultative.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Course {
    private int id;
    private String name;
    private int tutorId;
    private LocalDate startingDate;
    private LocalDate finishingDate;
    private boolean active;
    private List<User> usersList;

}
