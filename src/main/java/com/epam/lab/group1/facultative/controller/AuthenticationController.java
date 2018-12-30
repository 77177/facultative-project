package com.epam.lab.group1.facultative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authenticator")
public class AuthenticationController {

    private final String loginViewName = "login";
    private final String registrationViewName = "register";
    private final String courseViewName = "course";

    /**
     * @return official project login page.
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView(loginViewName);
        return modelAndView;
    }

    /**
     * @return page of registration form/
     */
    @GetMapping("/registration")
    public ModelAndView registration() {
        boolean isAuthenticated = false;
        ModelAndView modelAndView = new ModelAndView(registrationViewName);
        return modelAndView;
    }

    /**
     * Receive information from user and passes it to AuthenticationService to process.
     *
     * @return page of all courses.
     */
    @PostMapping("/registration")
    public ModelAndView registerNewUser(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String email,
                                        @RequestParam String password,
                                        @RequestParam String position) {
        //TODO redirect param-list to AuthenticationService. Which should create new UserDetails and save in DB.
        //AuthenticationService creates new user, sets Authentication in SecurityContextHolder
        ModelAndView modelAndView = new ModelAndView(courseViewName);
        return modelAndView;
    }
}
