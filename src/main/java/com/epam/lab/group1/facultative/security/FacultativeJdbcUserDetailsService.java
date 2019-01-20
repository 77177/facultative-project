package com.epam.lab.group1.facultative.security;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import com.epam.lab.group1.facultative.persistance.UserDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the UserDetailsService. Retrieves data from DB and creates UserDetails.
 */
@Component
public class FacultativeJdbcUserDetailsService implements UserDetailsService {

    private final String studentRole = "student";
    private final String tutorRole = "tutor";
    private UserDAO userDAO;
    private CourseDAO courseDAO;

    public FacultativeJdbcUserDetailsService(UserDAO userDAO, CourseDAO courseDAO) {
        this.userDAO = userDAO;
        this.courseDAO = courseDAO;
    }

    /**
     * Loads user data via UserDao and then depending on user role grants corresponding GrantedAuthority.
     *
     * @param username user's username to search for this user in the database.
     * @return UserDetails
     * @throws UsernameNotFoundException - if user was not found in the application's database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDAO.getByEmail(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User " + username + " does not exist"));
        SimpleGrantedAuthority grantedAuthority = null;
        boolean isStudent = false;
        switch (user.getPosition()) {
            case studentRole: {
                grantedAuthority = new SimpleGrantedAuthority(studentRole);
                isStudent = true;
                break;
            }
            case tutorRole: {
                grantedAuthority = new SimpleGrantedAuthority(tutorRole);
                break;
            }
        }
        List<GrantedAuthority> authorities = Arrays.asList(grantedAuthority);
        SecurityContextUser securityContextUser = new SecurityContextUser(username, user.getPassword(), authorities);
        securityContextUser.setUserId(user.getId());
        securityContextUser
                .setCourseIdList(courseDAO.getAllById(user.getId())
                        .stream()
                        .map(Course::getId)
                        .collect(Collectors.toList()));
        securityContextUser.setStudent(isStudent);
        securityContextUser.setFirstName(user.getFirstName());
        return securityContextUser;
    }
}

