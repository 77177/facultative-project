package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.FacultativeJdbcUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Creates new user account and authorizes him un the SecurityContext
 */
@Service
public class AuthenticationService {

    private FacultativeJdbcUserDetailsService userDetailsService;
    private UserService userService;

    public AuthenticationService(FacultativeJdbcUserDetailsService userDetailsService, UserService userService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Forwards PersonRegistrationFormDTO object to UserService to retrieve User entity and to save it in database.
     * Creates UserDetails and sets it into SecurityContextHolder.
     *
     * @param personRegistrationFormDTO user's data from registration form.
     */
    public User createUser(PersonRegistrationFormDTO personRegistrationFormDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        personRegistrationFormDTO.setPassword(encoder.encode(personRegistrationFormDTO.getPassword()));
        return userService.createUserFromDto(personRegistrationFormDTO);
    }
}
