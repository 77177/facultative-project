package com.epam.lab.group1.facultative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authenticator")
public class AuthenticationController {

    private final String loginViewName = "loginPage";
    private final String registrationViewName = "register";

    /**
     * @return official project login page.
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView(loginViewName);
        return modelAndView;
    }

    /**
     * @return page of registration form.
     */
    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView(registrationViewName);
        return modelAndView;
    }
}
