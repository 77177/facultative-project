package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.config.DaoConfigTest;
import com.epam.lab.group1.facultative.config.application.MainContextConfig;
import com.epam.lab.group1.facultative.config.application.WebConfig;
import com.epam.lab.group1.facultative.config.security.WebSecurityApplicationConfigurer;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.view.ViewType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.epam.lab.group1.facultative.view.ViewType.ERROR;
import static com.epam.lab.group1.facultative.view.ViewType.FEEDBACK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, MainContextConfig.class, DaoConfigTest.class, WebSecurityApplicationConfigurer.class})
public class FeedBackControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testCreateFeedbackNonSubscribedUser() throws Exception {
        String username = "1tutor@gmail.com";
        String password = "1";
        SimpleGrantedAuthority tutorAuthority = new SimpleGrantedAuthority("tutor");
        SecurityContextUser securityContextUser = new SecurityContextUser(username, password, Arrays.asList(tutorAuthority));
        mockMvc.perform(MockMvcRequestBuilders.post("/feedback/")
            .with(csrf())
            .param("studentId", "5")
            .param("courseId", "2")
            .param("text", "feedbacktext")
            .param("mark", "5")
            .with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ERROR.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("userMessage", "message", "stackTrace"));
    }

    @Test
    public void testCreateFeedbackSubscribedUser() throws Exception {
        String username = "1tutor@gmail.com";
        String password = "1";
        SimpleGrantedAuthority tutorAuthority = new SimpleGrantedAuthority("tutor");
        SecurityContextUser securityContextUser = new SecurityContextUser(username, password, Arrays.asList(tutorAuthority));
        mockMvc.perform(MockMvcRequestBuilders.post("/feedback/")
            .with(csrf())
            .param("studentId", "6")
            .param("courseId", "2")
            .param("text", "feedbacktext")
            .param("mark", "5")
            .with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("student", "course", "feedback"));
    }

    @Test
    public void testGetFeedbackPageAnonymous() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/5/course/1/").with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/1/course/5/").with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/155/course/155/").with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
    }

    @Test
    public void testGetFeedbackPageProperlyLoggedStudentSubscribed() throws Exception {
        String username = "1student@gmail.com";
        String password = "1";
        SimpleGrantedAuthority studentAuthority = new SimpleGrantedAuthority("student");
        SecurityContextUser securityContextUser = new SecurityContextUser(username, password, Arrays.asList(studentAuthority));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/5/course/1/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.FEEDBACK.viewName));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/6/course/1/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.FEEDBACK.viewName));

        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/6/course/2/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.FEEDBACK.viewName));

    }

    @Test
    public void testGetFeedbackPageProperlyLoggedStudentNonSubscribed() throws Exception {
        String username = "1student@gmail.com";
        String password = "1";
        SimpleGrantedAuthority studentAuthority = new SimpleGrantedAuthority("student");
        SecurityContextUser securityContextUser = new SecurityContextUser(username, password, Arrays.asList(studentAuthority));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/5/course/2/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/6/course/3/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));
    }

    @Test
    public void testGetFeedbackPageProperlyLoggedTutorCourseOwner() throws Exception {
        String username = "1tutor@gmail.com";
        String password = "1";
        SimpleGrantedAuthority tutorAuthority = new SimpleGrantedAuthority("tutor");
        SecurityContextUser securityContextUser = new SecurityContextUser(username, password, Arrays.asList(tutorAuthority));

        //see my self feedback from my course
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/2/course/2/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));

        //another non subscribed tutor from my course
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/3/course/2/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));

        //Non subscribed student from my course
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/5/course/2/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));

        //Subscribed student from my course
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/6/course/2/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.FEEDBACK.viewName));
    }

    @Test
    public void testGetFeedbackPageProperlyLoggedTutorNotCourseOwner() throws Exception {
        String username = "1tutor@gmail.com";
        String password = "1";
        SimpleGrantedAuthority tutorAuthority = new SimpleGrantedAuthority("tutor");
        SecurityContextUser securityContextUser = new SecurityContextUser(username, password, Arrays.asList(tutorAuthority));

        //see non subscribed me mine feedback from another course
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/2/course/3/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));

        //Non subscribed student from other course
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/5/course/3/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));

        //Subscribed student from another course
        mockMvc
            .perform(MockMvcRequestBuilders.get("/feedback/user/6/course/1/").with(user(securityContextUser)))
            .andExpect(MockMvcResultMatchers.view().name(ViewType.FEEDBACK.viewName));
    }
}