package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
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

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import static com.epam.lab.group1.facultative.controller.ViewName.FEEDBACK;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/feedbackControllerTestContext.xml")
public class FeedBackControllerTest {

    private MockMvc mockMvc;

    public FeedBackControllerTest() {
        CourseService courseService = mock(CourseService.class);
        when(courseService.getById(0)).thenReturn(new Course());

        FeedBackService feedBackService = mock(FeedBackService.class);
        when(feedBackService.getFeedBack(0, 0)).thenReturn(new FeedBack());

        UserService userService = mock(UserService.class);
        when(userService.getById(0)).thenReturn(new User());

        mockMvc = MockMvcBuilders
            .standaloneSetup(new FeedBackController(userService, courseService, feedBackService))
            .build();
    }

    @Test
    public void testCreateFeedback() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/feedback/"))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK))
            .andExpect(MockMvcResultMatchers.model().attributeExists("student", "course", "feedback"));
    }

    @Test
    public void testGetFeedbackPage() throws Exception {
        //TODO exceptionHandler should be implemented.
        mockMvc.perform(MockMvcRequestBuilders.get("/feedback/course/0"))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK))
            .andExpect(MockMvcResultMatchers.model().attributeExists("student", "course", "feedback"));
    }
}