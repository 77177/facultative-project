package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
import com.epam.lab.group1.facultative.view.builder.UserViewBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static com.epam.lab.group1.facultative.view.ViewType.COURSE;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/userControllerTestContex.xml")
public class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private CourseService courseService;
    private UserViewBuilder userViewBuilder;
    private ExceptionModelAndViewBuilder exceptionModelAndViewBuilder;

    public UserControllerTest() {
        this.userService = mock(UserService.class);
        this.courseService = mock(CourseService.class);
        this.exceptionModelAndViewBuilder = mock(ExceptionModelAndViewBuilder.class);
        this.userViewBuilder = mock(UserViewBuilder.class);

        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new UserController(userService, courseService, exceptionModelAndViewBuilder,
                userViewBuilder))
            .build();
    }

    @Test
    public void testSendRedirectToProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile"))
            .andExpect(MockMvcResultMatchers.view().name(COURSE.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("courseList"));
    }

    @Test
    public void testAction() throws Exception {

        //TODO tests in authentication zone.
        //Out of authentication
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1/course/1/leave"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1/course/1/subscribe"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
    }
}