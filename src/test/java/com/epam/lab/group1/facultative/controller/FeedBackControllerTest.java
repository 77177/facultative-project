package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
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
        when(courseService.getById(1)).thenReturn(Optional.of(new Course()));
        FeedBackService feedBackService = mock(FeedBackService.class);
        when(feedBackService.getFeedBack(0, 0)).thenReturn(new FeedBack());
        mockMvc = MockMvcBuilders
            .standaloneSetup(new FeedBackController(courseService, feedBackService))
            .build();
    }

    @Test
    public void testCreateFeedback() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/feedback/"))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK))
            .andExpect(MockMvcResultMatchers.model().attributeExists("feedback", "course"));
    }

    @Test
    public void testGetFeedbackPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/feedback/user/0/course/0"))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK))
            .andExpect(MockMvcResultMatchers.model().attributeExists("feedback", "course"));
    }
}