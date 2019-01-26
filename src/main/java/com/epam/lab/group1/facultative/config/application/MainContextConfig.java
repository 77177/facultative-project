package com.epam.lab.group1.facultative.config.application;

import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@ComponentScans(value = {
    @ComponentScan("com.epam.lab.group1.facultative.config.application"),
    @ComponentScan("com.epam.lab.group1.facultative.service"),
    @ComponentScan("com.epam.lab.group1.facultative.dto"),
    @ComponentScan("com.epam.lab.group1.facultative.model")
    }
)
public class MainContextConfig {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        return localeResolver;
    }

    @Bean
    public ExceptionModelAndViewBuilder exceptionModelAndViewBuilder() {
        return new ExceptionModelAndViewBuilder();
    }

}
