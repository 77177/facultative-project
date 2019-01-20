package com.epam.lab.group1.facultative.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import static com.epam.lab.group1.facultative.controller.ViewName.LOGIN;
import static com.epam.lab.group1.facultative.controller.ViewName.REGISTER;

@Controller
@RequestMapping("/authenticator")
public class AuthenticationController {

    @Autowired
    private Logger logger;

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
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView(REGISTER);
        return modelAndView;
    }
}
