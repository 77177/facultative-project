package com.epam.lab.group1.facultative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authenticator")
public class AuthenticationController {

    private final String loginViewName = "login";
    private final String registrationViewName = "registration";

    /**
     * @return official project login page.
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView(loginViewName);
        return modelAndView;
    }

    /**
     * @return official project login page.
     */
    @RequestMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView(registrationViewName);
        return modelAndView;
    }
}
