package com.epam.lab.group1.facultative.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistrationAspect {

//    @Before(value = "execution(* loadUserByUsername(*))")
    public void register() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
    }
}
