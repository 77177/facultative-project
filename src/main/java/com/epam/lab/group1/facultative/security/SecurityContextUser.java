package com.epam.lab.group1.facultative.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class SecurityContextUser extends User {

    private int userId;
    private String firstName;
    private boolean isStudent;
    private List<Integer> courseIdList;

    public SecurityContextUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
