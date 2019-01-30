package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.controller.LocaleHolder;
import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration("/service/courseServiceTestContext.xml")
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private DataSource dataSource;


    @Before
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("/dao/sql/create_script.sql"),
                new ClassPathResource("/dao/sql/fill_script.sql"));
        populator.execute(this.dataSource);
    }

    @Test
    public void testGetById() {
        Course course = courseService.getById(1);
        assertEquals("COURSE_1", course.getName());
        assertEquals(1, course.getId());
        assertEquals(1, course.getTutorId());
        assertEquals(LocalDate.of(2019, 02, 01), course.getStartingDate());
        assertEquals(LocalDate.of(2019,03,01), course.getFinishingDate());
        assertEquals(Boolean.TRUE, course.isActive());
    }

    @Test
    public void testGetAllById() {
        int page = 0;
        User user = new User();
        user.setId(1);
        user.setPosition("tutor");
        List<Course> allCourseIdbyUserId = courseService.getAllByUserId(user.getId(), page);
        assertEquals(1, allCourseIdbyUserId.get(0).getId());
        assertEquals("COURSE_1", allCourseIdbyUserId.get(0).getName());
        user.setPosition("student");
        List<Course> allCourseIdbyUserId1 = courseService.getAllByUserId(user.getId(), page);
        assertEquals(1, allCourseIdbyUserId1.get(0).getId());
        assertEquals("COURSE_1", allCourseIdbyUserId1.get(0).getName());
    }

}
