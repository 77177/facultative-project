package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.service.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static com.epam.lab.group1.facultative.view.ViewType.LOGIN;
import static com.epam.lab.group1.facultative.view.ViewType.REGISTER;

@Controller
@RequestMapping("/authenticator")
public class AuthenticationController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * @return official project LOGIN page.
     */
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView(LOGIN.viewName);
    }

    /**
     * @return page of REGISTER form.
     */
    @GetMapping("/registration")
    public ModelAndView registrationPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView(REGISTER.viewName);
        if (error != null) {
            logger.error("User with this email already exists");
            modelAndView.addObject("error", "User with this email already exists");
        }
        return modelAndView;
    }

    /**
     * @return redirect to /user/profile in case succeeded registration, /authenticator/registration?error=true if
     * failed.
     */
    @PostMapping("/registration")
    public ModelAndView registration(@ModelAttribute User user) {
        if (authenticationService.registerNewUser(user)) {
            return new ModelAndView(new RedirectView("/user/profile"));
        }
        return new ModelAndView(new RedirectView("/authenticator/registration?error=true"));
    }
}