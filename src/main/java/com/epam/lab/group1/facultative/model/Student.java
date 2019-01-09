package com.epam.lab.group1.facultative.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int studentId;
    private String studentFirstName;
    private String studentLastName;
    private String username;
    private String password;
    private List<Course> courses;
}
