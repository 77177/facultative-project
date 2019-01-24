package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.service.CourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/authenticationControllerTestContext.xml")
public class WelcomeControllerTest {

    private MockMvc mockMvc;

    public WelcomeControllerTest() {
        CourseService courseService = mock(CourseService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new WelcomeController(courseService))
                .build();
    }

    @Test
    public void testWelcome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(""))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/"));
        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/"));
        mockMvc.perform(MockMvcRequestBuilders.get("/tutor"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/"));
        mockMvc.perform(MockMvcRequestBuilders.get("/mordor"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/"));
    }
}