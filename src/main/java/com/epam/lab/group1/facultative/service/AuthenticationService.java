package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.service.security.FacultativeJdbcUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public void createAndAuthorizeUser(PersonRegistrationFormDTO personRegistrationFormDTO) {

        //save new user to db
        userService.createUserFromDto(personRegistrationFormDTO);

        //create and set UserDetails into SecurityContextHolder
        String email = personRegistrationFormDTO.getEmail();
        String password = personRegistrationFormDTO.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
