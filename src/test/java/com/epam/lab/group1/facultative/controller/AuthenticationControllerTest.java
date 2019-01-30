package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.config.DaoConfigTest;
import com.epam.lab.group1.facultative.config.application.MainContextConfig;
import com.epam.lab.group1.facultative.config.application.WebConfig;
import com.epam.lab.group1.facultative.config.security.WebSecurityApplicationConfigurer;
import com.epam.lab.group1.facultative.persistance.UserDAO;
import com.epam.lab.group1.facultative.security.FacultativeJdbcUserDetailsService;
import com.epam.lab.group1.facultative.service.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static com.epam.lab.group1.facultative.view.ViewType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, MainContextConfig.class, DaoConfigTest.class, WebSecurityApplicationConfigurer.class})
public class AuthenticationControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private FacultativeJdbcUserDetailsService userDetailsService;
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("/dao/sql/create_script.sql"),
                new ClassPathResource("/dao/sql/fill_script.sql"));
        populator.execute(this.dataSource);
    }

    @Test
    public void testLoginAnonymous() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/authenticator/login")
                .with(anonymous()))
                .andExpect(MockMvcResultMatchers.view().name(LOGIN.viewName));
    }

    @Test
    public void testPostLogin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .with(anonymous())
                        .with(csrf())
                .param("username", "0tutor@gmail.com")
                .param("password", "0"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/?pageNumber=0"));
        mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .with(anonymous())
                        .with(csrf())
                .param("username", "0student@gmail.com")
                .param("password", "0"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/?pageNumber=0"));
    }

    @Test
    public void testGetRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authenticator/registration")
                .with(anonymous()))
                .andExpect(MockMvcResultMatchers.view().name(REGISTER.viewName));
    }

    @Test
    public void testPostRegistrationStudent() throws Exception {
        assertEquals(5, userDAO.getAllStudents().size());
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticator/registration")
                .with(csrf())
                .with(anonymous())
                .param("firstName", "Mao")
                .param("lastName", "Dao")
                .param("email", "mao@dao.com")
                .param("password", "maodao")
                .param("position", "student"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
        assertEquals(6, userDAO.getAllStudents().size());
    }

    @Test
    public void testPostRegistrationTutor() throws Exception {
        assertEquals(4, userDAO.getAllTutors().size());
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticator/registration")
                .with(csrf())
                .with(anonymous())
                .param("firstName", "Mao")
                .param("lastName", "Dao")
                .param("email", "mao@dao.com")
                .param("password", "maodao")
                .param("position", "tutor"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
        assertEquals(5, userDAO.getAllTutors().size());

    }

    @Test
    public void testPostRegistrationTutorExistingEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticator/registration")
                .with(csrf())
                .with(anonymous())
                .param("firstName", "Mao")
                .param("lastName", "Dao")
                .param("email", "0tutor@gmail.com")
                .param("password", "maodao")
                .param("position", "tutor"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/authenticator/registration?error=true"));
    }

    @Test
    public void testPostRegistrationStudentExistingEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticator/registration")
                .with(csrf())
                .with(anonymous())
                .param("firstName", "Mao")
                .param("lastName", "Dao")
                .param("email", "0student@gmail.com")
                .param("password", "maodao")
                .param("position", "student"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/authenticator/registration?error=true"));
    }
}