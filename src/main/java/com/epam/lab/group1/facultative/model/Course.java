package com.epam.lab.group1.facultative.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Course {
    private int id;
    private String name;
    private String teacherName;
    private Date startDate;
    private Date finalDate;

}
