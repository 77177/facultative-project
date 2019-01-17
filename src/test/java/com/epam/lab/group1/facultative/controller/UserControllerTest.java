package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.UserService;
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

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/userControllerTestContex.xml")
public class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private CourseService courseService;
    private final String studentViewName = "student";
    private final String tutorViewName = "tutor";
    private final String courseViewName = "course";

    public UserControllerTest() {
        this.userService = mock(UserService.class);
        this.courseService = mock(CourseService.class);
        MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new UserController(userService, courseService))
            .build();
        this.mockMvc = mockMvc;
    }

    @Test
    public void testSendRedirectToProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile"))
            .andExpect(MockMvcResultMatchers.view().name(courseViewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("courseList"));
    }

    @Test
    public void testAction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1/course/1?action=leave"))
            .andExpect(MockMvcResultMatchers.view().name(studentViewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user", "courseList"));
        //TODO check action performance

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1/course/1?action=subscribe"))
            .andExpect(MockMvcResultMatchers.view().name(studentViewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user", "courseList"));
        //TODO check action performance

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1/course/1"))
            .andExpect(MockMvcResultMatchers.view().name(courseViewName));
    }
}