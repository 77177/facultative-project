package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Creates new user account and authorizes him un the SecurityContext
 */
@Service
public class AuthenticationService implements AuthenticationServiceInterface {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    /**
     * Forwards PersonRegistrationFormDTO object to UserService to retrieve User entity and to save it in database.
     * Creates UserDetails and sets it into SecurityContextHolder.
     *
     * @param user user's data from registration form.
     */
    @Override
    public boolean registerNewUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userService.create(user);
            logger.debug("Created new user: " + user);
        } catch (Exception e) {
            logger.error("Error during user creation: " + user, e);
            return false;
        }
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password);
            Authentication authentication = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("User " + authentication + " has successfully authenticated");
            return true;
        } catch (AuthenticationException e) {
            logger.error("User " + user + " has failed authentication.", e);
            return false;
        }
    }
}