package com.epam.lab.group1.facultative.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int student_id;
    private String student_first_name;
    private String student_last_name;
    private String username;
    private String password;
    private List<Course> courses;
}
