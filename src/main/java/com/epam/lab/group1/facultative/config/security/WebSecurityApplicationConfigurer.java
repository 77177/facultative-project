package com.epam.lab.group1.facultative.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@ComponentScans({
        @ComponentScan("com.epam.lab.group1.facultative.config.security"),
        @ComponentScan("com.epam.lab.group1.facultative.security")
})
public class WebSecurityApplicationConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Filter registrationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/profile**").hasAnyAuthority("student", "tutor")
                .antMatchers("/feedback/**").hasAnyAuthority("student", "tutor")
                .antMatchers("/course/action/**").hasAuthority("tutor")
            .and()
                .addFilterBefore(registrationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
                .loginPage("/authenticator/login")
                .loginProcessingUrl("/login")
                .failureUrl("/authenticator/login")
                .defaultSuccessUrl("/user/profile")
            .and()
            .userDetailsService(userDetailsService)
            .logout()
                .logoutUrl("/course")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

















