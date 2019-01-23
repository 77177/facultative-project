package com.epam.lab.group1.facultative.config.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = {
    @ComponentScan("com.epam.lab.group1.facultative.config.application"),
    @ComponentScan("com.epam.lab.group1.facultative.service"),
    @ComponentScan("com.epam.lab.group1.facultative.dto"),
    @ComponentScan("com.epam.lab.group1.facultative.model")
    }
)
public class MainContextConfig {

}
