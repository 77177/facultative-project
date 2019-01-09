package com.epam.lab.group1.facultative.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tutor {
    private int tutorId;
    private String tutorFirstName;
    private String tutorLastName;
    private String username;
    private String password;
    private List<Course> courses;
}
