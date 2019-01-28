package com.epam.lab.group1.facultative.config.application;

import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import org.springframework.context.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableAspectJAutoProxy
@ComponentScans(value = {
    @ComponentScan("com.epam.lab.group1.facultative.config.application"),
    @ComponentScan("com.epam.lab.group1.facultative.service"),
    @ComponentScan("com.epam.lab.group1.facultative.dto"),
    @ComponentScan("com.epam.lab.group1.facultative.model"),
    @ComponentScan("com.epam.lab.group1.facultative.view")
    }
)
public class MainContextConfig {

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public ExceptionModelAndViewBuilder exceptionModelAndViewBuilder() {
        return new ExceptionModelAndViewBuilder();
    }

}
