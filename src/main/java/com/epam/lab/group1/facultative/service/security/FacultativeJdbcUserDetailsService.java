package com.epam.lab.group1.facultative.service.security;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.persistance.UserDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserDetailsService. Retrieves data from DB and creates UserDetails.
 */
@Component
public class FacultativeJdbcUserDetailsService implements UserDetailsService {

    private final String studentRole = "student";
    private final String tutorRole = "tutor";
    private UserDAO userDAO;

    public FacultativeJdbcUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Loads user data via UserDao and then depending on user role grants corresponding GrantedAuthority.
     *
     * @param email user's email to search for this user in the database.
     * @return UserDetails
     * @throws UsernameNotFoundException - if user was not found in the application's database.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDAO.getByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            SimpleGrantedAuthority grantedAuthority = null;
            switch (user.getPosition()) {
                case studentRole: {
                    grantedAuthority = new SimpleGrantedAuthority(studentRole);
                    break;
                }
                case tutorRole: {
                    grantedAuthority = new SimpleGrantedAuthority(tutorRole);
                    break;
                }
            }
            List<GrantedAuthority> authorities = Arrays.asList(grantedAuthority);
            return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User " + email + " does not exist");
        }
    }
}
