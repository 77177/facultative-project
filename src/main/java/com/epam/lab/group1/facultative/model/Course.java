package com.epam.lab.group1.facultative.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Course {
    private int id;
    private String name;
    private String teacherName;
    private Date startDate;
    private Date finalDate;
    private List<String> studentsList;

}
