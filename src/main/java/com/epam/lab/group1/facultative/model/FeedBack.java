package com.epam.lab.group1.facultative.model;

import lombok.Data;

@Data
public class FeedBack {
    private int studentId;
    private int courseId;
    private String string;
    private int Mark;
}
