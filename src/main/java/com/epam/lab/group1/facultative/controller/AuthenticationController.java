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
        logger.info("Caught request " + request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView(LOGIN);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }

    /**
     * @return page of REGISTER form.
     */
    @GetMapping("/registration")
    public ModelAndView registration(HttpServletRequest request, @RequestParam(required = false) String error) {
        logger.info("Caught request " + request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView(REGISTER);
        logger.info("Create ModelAndView with View " + modelAndView.getViewName());
        if (error != null) {
            modelAndView.addObject("error", "User with this email already exists");
            logger.info("User already exists -> adding Model: " + modelAndView.getModel());
        }
        logger.info("Send Model to " + modelAndView.getViewName());
        return modelAndView;
    }
}