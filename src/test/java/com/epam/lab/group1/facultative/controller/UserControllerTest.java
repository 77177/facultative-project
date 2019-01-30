package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.config.DaoConfigTest;
import com.epam.lab.group1.facultative.config.application.MainContextConfig;
import com.epam.lab.group1.facultative.config.application.WebConfig;
import com.epam.lab.group1.facultative.config.security.WebSecurityApplicationConfigurer;
import com.epam.lab.group1.facultative.security.FacultativeJdbcUserDetailsService;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
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
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataSource dataSource;

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
    public void testSendRedirectToProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile").with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/").with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
    }

    @Test
    public void testSendRedirectToProfileStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile").with(user(getStudentAccount())))
            .andExpect(MockMvcResultMatchers.view().name(USER_STUDENT.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user", "pageNumber", "courseList"));
    }

    @Test
    public void testSendRedirectToProfileTutor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile").with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.view().name(USER_TUTOR.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user", "pageNumber", "courseList"));
    }

    @Test
    public void testActionLeaveSubscribedUser() throws Exception {
        SecurityContextUser studentAccount = getStudentAccount();
        assertEquals(2, studentAccount.getCourseIdList().size());
        mockMvc
            .perform(MockMvcRequestBuilders.get("/user/" + studentAccount.getUserId() + "/course/1/leave")
            .with(user(studentAccount)))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/1"));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/user/" + studentAccount.getUserId() + "/course/2/leave")
            .with(user(studentAccount)))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/2"));
        assertEquals(0, studentAccount.getCourseIdList().size());
    }

    @Test
    public void testActionLeaveNonSubscribedUser() throws Exception {
        SecurityContextUser studentAccount = getStudentAccount();
        assertEquals(2, studentAccount.getCourseIdList().size());
        mockMvc
            .perform(MockMvcRequestBuilders.get("/user/" + studentAccount.getUserId() + "/course/3/leave")
            .with(user(studentAccount)))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/3"));
        assertEquals(2, studentAccount.getCourseIdList().size());
    }

    @Test
    public void testActionSubscribeNonSubscribedUser() throws Exception {
        SecurityContextUser studentAccount = getStudentAccount();
        assertEquals(2, studentAccount.getCourseIdList().size());
        mockMvc
            .perform(MockMvcRequestBuilders.get("/user/" + studentAccount.getUserId() + "/course/3/subscribe")
            .with(user(studentAccount)))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/3"));
        assertEquals(3, studentAccount.getCourseIdList().size());
    }

    @Test
    public void testActionSubscribeSubscribedUser() throws Exception {
        SecurityContextUser studentAccount = getStudentAccount();
        assertEquals(2, studentAccount.getCourseIdList().size());
        mockMvc
            .perform(MockMvcRequestBuilders.get("/user/" + studentAccount.getUserId() + "/course/2/subscribe")
            .with(user(studentAccount)))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/2"));
        assertEquals(2, studentAccount.getCourseIdList().size());
    }

    private SecurityContextUser getStudentAccount() {
        String username = "1student@gmail.com";
        SecurityContextUser student = (SecurityContextUser) userDetailsService.loadUserByUsername(username);
        return student;
    }

    private SecurityContextUser getTutorAccount() {
        String username = "1tutor@gmail.com";
        SecurityContextUser tutor = (SecurityContextUser) userDetailsService.loadUserByUsername(username);
        return tutor;
    }
}