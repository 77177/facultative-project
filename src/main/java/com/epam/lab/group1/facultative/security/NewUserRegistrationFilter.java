package com.epam.lab.group1.facultative.security;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.service.AuthenticationService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component(value = "registrationFilter")
public class NewUserRegistrationFilter implements Filter {

    private final Logger logger = Logger.getLogger(this.getClass());
    private AuthenticationService authenticationService;
    private UserService userService;

    public NewUserRegistrationFilter(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        boolean isRegistration = servletRequest.getParameter("registration") != null;
        logger.debug("Entering to the new user creation zone.");
        if (isRegistration) {
            PersonRegistrationFormDTO personRegistrationFormDTO = formDtoFromRequest(servletRequest);
            authenticationService.createUser(personRegistrationFormDTO);
        }
        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy () {

    }

    private PersonRegistrationFormDTO formDtoFromRequest (ServletRequest req){
        PersonRegistrationFormDTO dto = new PersonRegistrationFormDTO();
        dto.setFirstName(req.getParameter("firstName"));
        dto.setLastName(req.getParameter("lastName"));
        dto.setEmail(req.getParameter("username"));
        dto.setPosition(req.getParameter("position"));
        dto.setPassword(req.getParameter("password"));
        logger.debug("Created PersonRegistrationFormDTO from request: " + dto);
        return dto;
    }
}
