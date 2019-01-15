package com.epam.lab.group1.facultative.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/authenticationControllerTestContext.xml")
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Test
    public void testLogin() {

    }

    @Test
    public void TestRegistration() {

    }
}