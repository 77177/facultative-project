package com.epam.lab.group1.facultative.security;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.service.AuthenticationService;
import com.epam.lab.group1.facultative.service.UserService;

import javax.servlet.*;
import java.io.IOException;

public class NewUserRegistrationFilter implements Filter {

    private UserService userService;
    private AuthenticationService authenticationService;

    public NewUserRegistrationFilter(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        boolean isRegistration = servletRequest.getParameter("registration") != null;
        if (isRegistration) {
            PersonRegistrationFormDTO personRegistrationFormDTO = formDtoFromRequest(servletRequest);
            authenticationService.createUser(personRegistrationFormDTO);
        }
        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy() {

    }

    private PersonRegistrationFormDTO formDtoFromRequest(ServletRequest req) {
        PersonRegistrationFormDTO dto = new PersonRegistrationFormDTO();
        dto.setFirstName(req.getParameter("firstName"));
        dto.setLastName(req.getParameter("lastName"));
        dto.setEmail(req.getParameter("username"));
        dto.setPosition(req.getParameter("position"));
        dto.setPassword(req.getParameter("password"));
        return dto;
    }
}
