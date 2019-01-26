package com.epam.lab.group1.facultative.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.epam.lab.group1.facultative.controller.ViewName.LOGIN;
import static com.epam.lab.group1.facultative.controller.ViewName.REGISTER;

@Controller
@RequestMapping("/authenticator")
public class AuthenticationController {

    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * @return official project LOGIN page.
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(LOGIN);
        return modelAndView;
    }

    /**
     * @return page of REGISTER form.
     */
    @GetMapping("/registration")
    public ModelAndView registration(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView(REGISTER);
        if (error != null) {
            modelAndView.addObject("error", "User with this email already exists");
        }
        return modelAndView;
    }
}