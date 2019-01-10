package com.epam.lab.group1.facultative.service.security;

import com.epam.lab.group1.facultative.dto.UserDto;
import com.epam.lab.group1.facultative.persistance.security.UserDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of the UserDetailsService. Retrieves data from DB and creates UserDetails.
 */
@Component
public class FacultativeJdbcUserDetailsService implements UserDetailsService {

    private final String studentRole = "student";
    private final String tutorRole = "tutor";
    private UserDao userDao;

    public FacultativeJdbcUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Loads user data via UserDao and then depending on user role grants corresponding GrantedAuthority.
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userByEmail = userDao.getUserByEmail(email);
        SimpleGrantedAuthority grantedAuthority = null;
        switch (userByEmail.getJobPosition()) {
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
        return new User(email, userByEmail.getPassword(), authorities);
    }
}
