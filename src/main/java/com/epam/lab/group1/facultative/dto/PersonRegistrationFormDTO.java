package com.epam.lab.group1.facultative.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Carries data from AuthenticationController to Service layer.
 * Contains all necessary information to create certain person entity.
 */
@Getter
@Setter
@Component
public class PersonRegistrationFormDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String position;
}
