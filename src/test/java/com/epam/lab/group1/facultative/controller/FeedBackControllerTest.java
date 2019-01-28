package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.exception.ExceptionModelAndViewBuilder;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import com.epam.lab.group1.facultative.service.CourseService;
import com.epam.lab.group1.facultative.service.FeedBackService;
import com.epam.lab.group1.facultative.service.UserService;
import com.epam.lab.group1.facultative.view.builder.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;

import static com.epam.lab.group1.facultative.view.ViewType.FEEDBACK;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/feedbackControllerTestContext.xml")
public class FeedBackControllerTest {

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

        mockMvc = MockMvcBuilders
            .standaloneSetup(new FeedBackController(userService, courseService, feedBackService,
                exceptionModelAndViewBuilder, feedbackViewBuilder))
            .build();
    }

    @Test
    public void testCreateFeedback() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/feedback/"))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("student", "course", "feedback"));
    }

    @Ignore
    @Test
    public void testGetFeedbackPage() throws Exception {
        //TODO exceptionHandler should be implemented.
        mockMvc.perform(MockMvcRequestBuilders.get("/feedback/user/3/course/1/"))
            .andExpect(MockMvcResultMatchers.view().name(FEEDBACK.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("feedback", "student", "course"));
    }
}