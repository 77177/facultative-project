package com.epam.lab.group1.facultative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final String studentPosition = "student";
    private final String tutorPosition = "tutor";

    @RequestMapping("/profile/{userId}")
    public String sendRedirectToProfile(@RequestParam String position) {
        switch (position) {
            case studentPosition: {
                int id = 5;
                return "redirect: /student/" + id;
            }
            case tutorPosition: {
                int id = 5;
                return "redirect: /tutor/" + id;
            }
            default: {
                return "redirect: /course";
            }
        }
    }
}
