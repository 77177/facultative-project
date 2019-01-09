package com.epam.lab.group1.facultative.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRegistrationFormDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String position;
}
