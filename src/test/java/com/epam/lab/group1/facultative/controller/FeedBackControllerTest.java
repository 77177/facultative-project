package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.config.DaoConfigTest;
import com.epam.lab.group1.facultative.config.application.DaoConfig;
import com.epam.lab.group1.facultative.config.application.MainContextConfig;
import com.epam.lab.group1.facultative.config.application.WebConfig;
import com.epam.lab.group1.facultative.config.security.WebSecurityApplicationConfigurer;
import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
import com.epam.lab.group1.facultative.service.UserService;
import com.epam.lab.group1.facultative.view.ViewType;
import com.epam.lab.group1.facultative.view.builder.*;
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

import static org.mockito.Mockito.mock;

import static com.epam.lab.group1.facultative.view.ViewType.FEEDBACK;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, MainContextConfig.class, DaoConfigTest.class, WebSecurityApplicationConfigurer.class})
//@ContextConfiguration("/controller/feedbackControllerTestContext.xml")
public class FeedBackControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private UserService userService;
    private CourseService courseService;
    private FeedBackService feedBackService;
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;
    private FeedbackViewBuilder feedbackViewBuilder;

    public FeedBackControllerTest() {
        this.feedbackViewBuilder = new FeedbackViewBuilder();
        this.exceptionModelAndViewBuilder = new ExceptionModelAndViewBuilder();
        ExceptionModelAndViewBuilder exceptionModelAndViewBuilder = mock(ExceptionModelAndViewBuilder.class);

        FeedBack feedBack = new FeedBack();
        feedBack.setStudentId(0);
        feedBack.setCourseId(0);

        this.userService = mock(UserService.class);
        when(userService.getById(0)).thenReturn(new User());

        this.courseService = mock(CourseService.class);
        when(courseService.getById(0)).thenReturn(new Course());

        this.feedBackService = mock(FeedBackService.class);
        when(feedBackService.getFeedBack(0, 0)).thenReturn(new FeedBack());

//        mockMvc = MockMvcBuilders
//            .standaloneSetup(new FeedBackController(userService, courseService, feedBackService,
//                exceptionModelAndViewBuilder, feedbackViewBuilder))
//            .build();
    }

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testCreateFeedback() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/feedback/"))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("student", "course", "feedback"));
    }

    @Test
    public void testGetFeedbackPageProperlyLoggedStudent() throws Exception {
        //TODO exceptionHandler should be implemented.
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



    }

    @Test
    public void testGetFeedbackPage() throws Exception {
        //TODO exceptionHandler should be implemented.
        String username = "1student@gmail.com";
        String password = "1";
        SimpleGrantedAuthority studentAuthority = new SimpleGrantedAuthority("student");
        SecurityContextUser securityContextUser = new SecurityContextUser(username, password, Arrays.asList(studentAuthority));

        //Student subscribed on course
        mockMvc
                .perform(MockMvcRequestBuilders.get("/feedback/user/5/course/1/").with(user(securityContextUser)))
                .andExpect(MockMvcResultMatchers.view().name(ViewType.FEEDBACK.viewName));

        //Student does not subscribed on the course
        mockMvc
                .perform(MockMvcRequestBuilders.get("/feedback/user/5/course/2/").with(user(securityContextUser)))
                .andExpect(MockMvcResultMatchers.view().name(ViewType.ERROR.viewName));

    }
}