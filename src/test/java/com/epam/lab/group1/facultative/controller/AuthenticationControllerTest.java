package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.epam.lab.group1.facultative.view.ViewType.LOGIN;
import static com.epam.lab.group1.facultative.view.ViewType.REGISTER;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/authenticationControllerTestContext.xml")
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    public AuthenticationControllerTest() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        LocaleHolder localeHolder = mock(LocaleHolder.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthenticationController(authenticationService))
                .build();
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authenticator/login"))
                .andExpect(MockMvcResultMatchers.view().name(LOGIN.viewName));
    }

    @Test
    public void TestRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authenticator/registration"))
                .andExpect(MockMvcResultMatchers.view().name(REGISTER.viewName));
    }
}