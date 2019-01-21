package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.model.User;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Creates new user account and authorizes him un the SecurityContext
 */
@Service
public class AuthenticationService {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
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
