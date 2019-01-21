package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
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

import java.util.Collections;

import static com.epam.lab.group1.facultative.controller.ViewName.COURSE;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_INFO;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_CREATE;
import static com.epam.lab.group1.facultative.controller.ViewName.COURSE_EDIT;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/courseControllerTestContext.xml")
public class CourseControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private CourseService courseService;

    public CourseControllerTest() {
        this.userService = mock(UserService.class);
        this.courseService = mock(CourseService.class);
        Course course = mock(Course.class);
        User user = mock(User.class);

        when(course.getTutorId()).thenReturn(1);
        when(courseService.getById(1)).thenReturn(course);
        when(user.getFullName()).thenReturn("Poligraf Poligrafovich");
        when(userService.getAllStudentByCourseId(1)).thenReturn(Collections.emptyList());
        when(userService.getById(1)).thenReturn(user);

        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new CourseController(courseService, userService))
            .build();
    }

    @Test
    public void testGetAllCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/"))
            .andExpect(MockMvcResultMatchers.view().name(COURSE))
            .andExpect(MockMvcResultMatchers.model().attributeExists("courseList"));
    }

    @Test
    public void tesGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/1"))
            .andExpect(MockMvcResultMatchers.view().name(COURSE_INFO))
            .andExpect(MockMvcResultMatchers.model().attributeExists("tutorName", "course", "studentList"));
    }

    @Test
    public void testGetCreateCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/create/1"))
            .andExpect(MockMvcResultMatchers.view().name(COURSE_CREATE));
    }

    @Test
    public void testPostCreateCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/create"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/delete/1"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    }

    @Test
    public void tesGetEditCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/1/action/edit/1"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("tutorId", "course"))
            .andExpect(MockMvcResultMatchers.view().name(COURSE_EDIT));
    }

    @Test
    public void testPostEditCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/edit"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    }

}