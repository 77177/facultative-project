package com.epam.lab.group1.facultative.service;

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

import javax.persistence.PersistenceException;
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
        assertEquals(LocalDate.of(2015, 11, 10), course.getStartingDate());
        assertEquals(LocalDate.of(2015,11,12), course.getFinishingDate());
        assertEquals(Boolean.TRUE, course.isActive());
    }

    @Test()
    public void testCreate() {
        Course course = new Course();
        course.setName("COURSE_3");
        course.setTutorId(1);
        course.setStartingDate(LocalDate.of(2019, 1, 30));
        course.setFinishingDate(LocalDate.of(2020, 1, 30));
        course.setActive(false);

        SingleCourseDto courseDto = new SingleCourseDto();
        courseDto.setCourse(course);
        courseService.create(course);

        assertEquals(courseDto.getCourse().getName(), courseService.getById(course.getId()).getName());
        assertEquals(courseDto.getCourse().getTutorId(), courseService.getById(course.getId()).getTutorId());
        assertEquals(courseDto.getCourse().getStartingDate(), courseService.getById(course.getId()).getStartingDate());
        assertEquals(courseDto.getCourse().getFinishingDate(), courseService.getById(course.getId()).getFinishingDate());
        assertEquals(courseDto.getCourse().isActive(), courseService.getById(course.getId()).isActive());

    }

    @Test()
    public void testUpdate() {
        Course course = courseService.getById(1);
        course.setName("New_Course_Name");
        courseService.update(course);
        assertEquals("New_Course_Name", courseService.getById(1).getName());
    }

    @Test
    public void testDeleteById() {
        courseService.deleteById(2);

        assertEquals(1, courseService.findAll().size());
        assertEquals(1, courseService.findAll().get(0).getId());
        assertEquals("COURSE_1", courseService.findAll().get(0).getName());
    }

    @Test
    public void testGetAllById() {
        User user = new User();
        user.setId(1);
        user.setPosition("tutor");
        List<Course> allCourseIdbyUserId = courseService.getAllByUserId(user.getId());
        assertEquals(1, allCourseIdbyUserId.get(0).getId());
        assertEquals("COURSE_1", allCourseIdbyUserId.get(0).getName());
        user.setPosition("student");
        List<Course> allCourseIdbyUserId1 = courseService.getAllByUserId(user.getId());
        assertEquals(1, allCourseIdbyUserId1.get(0).getId());
        assertEquals("COURSE_1", allCourseIdbyUserId1.get(0).getName());
    }

}