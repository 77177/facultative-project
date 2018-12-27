package com.epam.lab.group1.facultative.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Course {
    private int id;
    private String name;
    private String teacherName;
    private Date startDate;
    private Date finalDate;
    private List<Student> studentsList;

}
