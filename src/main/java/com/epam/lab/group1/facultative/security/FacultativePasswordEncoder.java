package com.epam.lab.group1.facultative.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Does not perform encoding.
 */
public class FacultativePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return String.valueOf(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(String.valueOf(rawPassword));
    }
}
