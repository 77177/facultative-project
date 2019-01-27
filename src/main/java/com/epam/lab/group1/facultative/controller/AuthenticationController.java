package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.FacultativeJdbcUserDetailsService;
import com.epam.lab.group1.facultative.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import static com.epam.lab.group1.facultative.controller.ViewName.LOGIN;
import static com.epam.lab.group1.facultative.controller.ViewName.REGISTER;

@Controller
@RequestMapping("/authenticator")
public class AuthenticationController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private FacultativeJdbcUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    /**
     * @return official project LOGIN page.
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView(LOGIN);
        return modelAndView;
    }

    /**
     * @return page of REGISTER form.
     */
    @GetMapping("/registration")
    public ModelAndView registrationPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView(REGISTER);
        if (error != null) {
            logger.error("User with this email already exists");
            modelAndView.addObject("error", "User with this email already exists");
        }
        return modelAndView;
    }

    /**
     * @return page of REGISTER form.
     */
    @PostMapping("/registration")
    public ModelAndView registration(@ModelAttribute User user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = user.getPassword();
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            userService.create(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView(new RedirectView("/authenticator/registration?error=true"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());


        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(encoder);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, password);

        Authentication authentication = daoAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ModelAndView(new RedirectView("/course/"));
        }
        return new ModelAndView(new RedirectView("/authenticator/registration?error=true"));
    }
}