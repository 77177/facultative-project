package com.epam.lab.group1.facultative.security;

import com.epam.lab.group1.facultative.dto.UserDto;
import com.epam.lab.group1.facultative.persistance.security.UserDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;

public class FacultativeJdbcUserDetailsService implements UserDetailsService {

    private final String studentRole = "student";
    private final String tutorRole = "tutor";
    private UserDao userDao;

    public FacultativeJdbcUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

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
        User user = new User(email, userByEmail.getPassword(), authorities);
        return user;
    }
}
