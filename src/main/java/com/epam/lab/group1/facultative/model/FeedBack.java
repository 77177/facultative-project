package com.epam.lab.group1.facultative.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "student_course")
public class FeedBack implements Serializable {

    @Id
    @Column(name = "student_id")
    private int studentId;

    @Id
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "feedback")
    private String text;

    @Column(name = "mark")
    private int mark;
}
