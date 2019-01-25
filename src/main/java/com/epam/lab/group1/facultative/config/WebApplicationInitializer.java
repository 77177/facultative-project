package com.epam.lab.group1.facultative.config;

import com.epam.lab.group1.facultative.config.application.DaoConfig;
import com.epam.lab.group1.facultative.config.application.MainContextConfig;
import com.epam.lab.group1.facultative.config.application.WebConfig;
import com.epam.lab.group1.facultative.config.security.WebSecurityApplicationConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MainContextConfig.class, DaoConfig.class, WebConfig.class, WebSecurityApplicationConfigurer.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
