package com.epam.lab.group1.facultative.security;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.service.AuthenticationService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
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
//        boolean isRegistration = servletRequest.getParameter("registration") != null;
//        logger.debug("Entering to the new user creation zone.");
//        if (isRegistration) {
//            User user = createUserFromRequest(servletRequest);
//            try {
//                authenticationService.processNewUser(user);
//            } catch (ConstraintViolationException e) {
//                ((HttpServletResponse)response).sendRedirect("/authenticator/registration?error=true");
//            }
//        }
        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy () {

    }

    private User createUserFromRequest(ServletRequest req){
        User user = new User();
        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setEmail(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setPosition(req.getParameter("position"));

        logger.debug("Created PersonRegistrationFormDTO from request: " + user);
        return user;
    }
}
