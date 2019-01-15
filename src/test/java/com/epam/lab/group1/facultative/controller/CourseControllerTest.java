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

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/courseControllerTestContext.xml")
public class CourseControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private CourseService courseService;
    private final String courseViewName = "course";
    private final String courseInfoViewName = "courseInfo";
    private final String createCourseView = "createCourse";
    private final String editCourseView = "editCourse";

    public CourseControllerTest() {
        this.userService = mock(UserService.class);
        this.courseService = mock(CourseService.class);
        MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CourseController(courseService, userService))
            .build();
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetAllCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/"))
            .andExpect(MockMvcResultMatchers.view().name(courseViewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("courseList"));
    }

    @Test
    public void testById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/1"))
            .andExpect(MockMvcResultMatchers.view().name(courseInfoViewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("courseInfo", "studentList"));
    }

    @Test
    public void testGetCreateCourse() throws Exception {
        //TODO resolve
//        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/createCourse"))
//            .andExpect(MockMvcResultMatchers.view().name(createCourseView));
    }

    @Test
    public void testPostCreateCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/createCourse"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/delete/1"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    }

    @Test
    public void tesEditCourse() throws Exception {
        //TODO resolve
//        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/editCourse"))
//            .andExpect(MockMvcResultMatchers.view().name(editCourseView));
    }

    @Test
    public void tesPostEditCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/course/action/editCourse")
            .param("tutorId","1")
            .param("courseId", "1"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    }
}