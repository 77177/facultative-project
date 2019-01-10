package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.model.Position;
import com.epam.lab.group1.facultative.service.security.FacultativeJdbcUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private FacultativeJdbcUserDetailsService userDetailsService;

    public AuthenticationService(FacultativeJdbcUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void createAccount(PersonRegistrationFormDTO personRegistrationFormDTO) {
        String position = personRegistrationFormDTO.getPosition();
        if (position.equals(Position.STUDENT.name())) {
            //TODO Send DTO to the StudentService
        } else if (position.equals(Position.TUTOR.name())) {
            //sends dto to the TutorService
        }
        String email = personRegistrationFormDTO.getEmail();
        String password = personRegistrationFormDTO.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
