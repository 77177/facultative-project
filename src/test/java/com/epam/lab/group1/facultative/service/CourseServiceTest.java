package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.junit.Before;
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
        course.setTutorId(5);
        course.setStartingDate(LocalDate.of(2016, 11, 10));
        course.setFinishingDate(LocalDate.of(2017, 11, 12));
        course.setActive(Boolean.TRUE);
        courseService.create(course);

        assertEquals(course.getName(), courseService.getById(course.getId()).getName());
        assertEquals(course.getTutorId(), courseService.getById(course.getId()).getTutorId());
        assertEquals(course.getStartingDate(), courseService.getById(course.getId()).getStartingDate());
        assertEquals(course.getFinishingDate(), courseService.getById(course.getId()).getFinishingDate());
        assertEquals(course.isActive(), courseService.getById(course.getId()).isActive());
    }

    @Test(expected = PersistenceException.class)
    public void testCreateWithWrongDate() {
        Course course = new Course();
        course.setName("COURSE_4");
        course.setTutorId(5);
        course.setStartingDate(LocalDate.of(2016, 11, 10));
        course.setFinishingDate(LocalDate.of(2015, 11, 12));
        course.setActive(Boolean.TRUE);
        courseService.create(course);
    }

    @Test(expected = PersistenceException.class)
    public void testCreateWithWrongName() {
        Course course = new Course();
        course.setName("");
        course.setTutorId(5);
        course.setStartingDate(LocalDate.of(2015, 11, 10));
        course.setFinishingDate(LocalDate.of(2016, 11, 12));
        course.setActive(Boolean.TRUE);
        courseService.create(course);
    }

    @Test()
    public void testUpdate() {
        Course course = courseService.getById(1);
        course.setName("New_Course_Name");
        courseService.update(course);
        assertEquals("New_Course_Name", courseService.getById(1).getName());
    }

    @Test(expected = PersistenceException.class)
    public void testUpdateWithWrongDate() {
        Course course = courseService.getById(1);
        course.setStartingDate(LocalDate.of(2020, 01,01));
        courseService.update(course);
    }

    @Test(expected = PersistenceException.class)
    public void testUpdateWithWrongName() {
        Course course = courseService.getById(1);
        course.setName("");
        courseService.update(course);
    }

    @Test
    public void testDeleteById() {
        courseService.deleteById(1);
        assertEquals(1, courseService.findAll().size());
        assertEquals(2, courseService.findAll().get(0).getId());
        assertEquals("COURSE_2", courseService.findAll().get(0).getName());
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
