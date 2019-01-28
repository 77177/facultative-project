package com.epam.lab.group1.facultative.model;

public enum UserPosition {

    STUDENT("student"),
    TUTOR("tutor");
    public String position;

    UserPosition(String position) {
        this.position = position;
    }
}
