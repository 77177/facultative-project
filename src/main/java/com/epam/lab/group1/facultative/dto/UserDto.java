package com.epam.lab.group1.facultative.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Carries security data retrieved from database.
 */
@Getter
@Setter
@Component
public class UserDto {

    private String email;
    private String password;
    private String jobPosition;
}
