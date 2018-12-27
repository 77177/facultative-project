package com.epam.lab.group1.facultative.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Student {
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private List<String> courses;

}
