package com.epam.lab.group1.facultative.controller;

import com.epam.lab.group1.facultative.config.DaoConfigTest;
import com.epam.lab.group1.facultative.config.application.MainContextConfig;
import com.epam.lab.group1.facultative.config.application.WebConfig;
import com.epam.lab.group1.facultative.config.security.WebSecurityApplicationConfigurer;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import com.epam.lab.group1.facultative.security.FacultativeJdbcUserDetailsService;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static com.epam.lab.group1.facultative.view.ViewType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, MainContextConfig.class, DaoConfigTest.class, WebSecurityApplicationConfigurer.class})
public class CourseControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private FacultativeJdbcUserDetailsService userDetailsService;
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
            new ClassPathResource("/dao/sql/create_script.sql"),
            new ClassPathResource("/dao/sql/fill_script.sql"));
        populator.execute(this.dataSource);
    }

    @Test
    public void testGetAllCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/").with(anonymous()))
            .andExpect(MockMvcResultMatchers.view().name(COURSE.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("courseList"));
        mockMvc.perform(MockMvcRequestBuilders.get("/").with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/"));
    }

    @Test
    public void tesGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/1").with(anonymous()))
            .andExpect(MockMvcResultMatchers.view().name(COURSE_INFO.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("tutorName", "course", "studentList"));
        mockMvc.perform(MockMvcRequestBuilders.get("/course/-1").with(anonymous()))
            .andExpect(MockMvcResultMatchers.view().name(ERROR.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("userMessage", "message", "stackTrace"));
    }

    @Test
    public void testGetCreateCourseAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/create/").with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
    }

    @Test
    public void testGetCreateCourseStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/create/").with(user(getStudentAccount())))
            .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void testPostCreateCourse() throws Exception {
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/create")
            .with(csrf())
            .param("name", "any new course")
            .param("tutorId", "2")
            .param("startingDate", "2019-04-01")
            .param("finishingDate", "2019-05-05")
            .param("active", "true")
            .with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    assertEquals(13, courseDAO.findAllActive(0, 100).size());
    }

    @Test
    public void testPostCreateCourseSameName() throws Exception {
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/create")
            .with(csrf())
            .param("name", "COURSE_1")
            .param("tutorId", "2")
            .param("startingDate", "2019-04-01")
            .param("finishingDate", "2019-05-05")
            .param("active", "true")
            .with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.view().name(COURSE_CREATE.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
    }

    @Test
    public void testEditCourse() throws Exception {
        int courseId = 2;
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/edit")
            .with(csrf())
            .param("name", "new name")
            .param("startingDate", "2019-04-01")
            .param("finishingDate", "2019-05-05")
            .param("active", "true")
            .param("id", String.valueOf(courseId))
            .with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/" + courseId));
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        assertEquals("new name", courseDAO.getById(courseId).getName());
    }

    @Test
    public void testEditCourseNameNonUnique() throws Exception {
        int courseId = 2;
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/edit")
            .with(csrf())
            .param("name", "COURSE_3")
            .param("startingDate", "2019-04-01")
            .param("finishingDate", "2019-05-05")
            .param("active", "true")
            .param("id", String.valueOf(courseId))
            .with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.view().name(COURSE_EDIT.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage", "course"));
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        assertEquals("COURSE_2", courseDAO.getById(courseId).getName());
    }

    @Test
    public void testEditCourseNotAnOwner() throws Exception {
        int courseId = 1;
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/edit")
            .with(csrf())
            .param("name", "COURSE_3")
            .param("startingDate", "2019-04-01")
            .param("finishingDate", "2019-05-05")
            .param("active", "true")
            .param("id", String.valueOf(courseId))
            .with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.view().name(COURSE_EDIT.viewName))
            .andExpect(MockMvcResultMatchers.model().attributeExists("course"));
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        assertEquals("COURSE_1", courseDAO.getById(courseId).getName());
    }

    @Test
    public void testEditCourseNonTutorStudent() throws Exception {
        int courseId = 2;
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/edit")
            .with(csrf())
            .param("name", "COURSE_3")
            .param("startingDate", "2019-04-01")
            .param("finishingDate", "2019-05-05")
            .param("active", "true")
            .param("id", String.valueOf(courseId))
            .with(user(getStudentAccount())))
            .andExpect(MockMvcResultMatchers.status().is(403));
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        assertEquals("COURSE_2", courseDAO.getById(courseId).getName());
    }

    @Test
    public void testEditCourseNonTutorAnonymous() throws Exception {
        int courseId = 2;
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        mockMvc.perform(MockMvcRequestBuilders.post("/course/action/edit")
            .with(csrf())
            .param("name", "COURSE_3")
            .param("startingDate", "2019-04-01")
            .param("finishingDate", "2019-05-05")
            .param("active", "true")
            .param("id", String.valueOf(courseId))
            .with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
        assertEquals(12, courseDAO.findAllActive(0, 100).size());
        assertEquals("COURSE_2", courseDAO.getById(courseId).getName());
    }

    @Test
    public void testDeleteCourseAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/delete/1")
            .with(anonymous()))
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/authenticator/login"));
    }

    @Test
    public void testDeleteCourseStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/delete/1")
            .with(user(getStudentAccount())))
            .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void testDeleteCourseTutor() throws Exception {
        int courseId = 2;
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/delete/" + courseId)
            .with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile"));
    }

    @Test
    public void testDeleteCourseTutorNotAnOwner() throws Exception {
        int courseId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/course/action/delete/" + courseId)
            .with(user(getTutorAccount())))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/course/" + courseId));
    }

    private SecurityContextUser getStudentAccount() {
        String username = "1student@gmail.com";
        SecurityContextUser student = (SecurityContextUser) userDetailsService.loadUserByUsername(username);
        return student;
    }

    private SecurityContextUser getTutorAccount() {
        String username = "1tutor@gmail.com";
        SecurityContextUser tutor = (SecurityContextUser) userDetailsService.loadUserByUsername(username);
        return tutor;
    }
}