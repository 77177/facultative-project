package com.epam.lab.group1.facultative.persistance;

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

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/userDaoTest/UserDaoSpringContextConfiguration.xml")
public class CourseDAOTest {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private DataSource dataSource;

    @Before
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("/userDaoTest/create_script.sql"),
                new ClassPathResource("/userDaoTest/fill_script.sql"));
        populator.execute(this.dataSource);
    }

    @Test
    public void testGetById() {
        Optional<Course> courseDAOById = courseDAO.getById(1);
        assertEquals("COURSE_1", courseDAOById.get().getName());
    }

    @Test
    public void testGetAllByUserID() {
        List<Course> allByUserID = courseDAO.getAllByUserID(3);
        assertEquals(1, allByUserID.size());
    }

    @Test
    public void testDeleteById() {
        courseDAO.deleteById(1);
        assertEquals(1, courseDAO.getList().size());
    }

    @Test
    public void testGetList() {
        List<Course> list = courseDAO.getList();
        assertEquals(2, list.size());
    }

    @Test
    public void testCreate() {
        Course course = new Course();
        course.setName("COURSE_3");
        course.setTutorId(1);
        course.setStartingDate(LocalDate.of(1, 1, 1));
        course.setFinishingDate(LocalDate.of(2, 2, 2));
        course.setActive(false);
        Optional<Course> courseReturn = courseDAO.create(course);
        assertEquals(course.getName(), courseDAO.getById(courseReturn.get().getId()).get().getName());
        assertEquals(course.getTutorId(), courseDAO.getById(courseReturn.get().getId()).get().getTutorId());
        assertEquals(course.getStartingDate(), courseDAO.getById(courseReturn.get().getId()).get().getStartingDate());
        assertEquals(course.getFinishingDate(), courseDAO.getById(courseReturn.get().getId()).get().getFinishingDate());
        assertEquals(course.isActive(), courseDAO.getById(courseReturn.get().getId()).get().isActive());
    }

    @Test
    public void testUpdate() {
        Optional<Course> byIdCourse = courseDAO.getById(1);
        byIdCourse.get().setName("New_Course_Name");
        courseDAO.update(byIdCourse.get());
        assertEquals("New_Course_Name", courseDAO.getById(1).get().getName());
    }

    @Test
    public void testGetAllCourseIdbyUserId() {
        User user = new User();
        user.setId(1);
        user.setPosition("tutor");
        List<Integer> allCourseIdbyUserId = courseDAO.getAllCourseIdbyUserId(user);
        assertEquals(1, allCourseIdbyUserId.get(0).longValue());
        user.setPosition("student");
        List<Integer> allCourseIdbyUserId1 = courseDAO.getAllCourseIdbyUserId(user);
        assertEquals(1, allCourseIdbyUserId1.get(0).longValue());
    }

    @Test
    public void testGetAllByTutorId() {
        User user = new User();
        user.setId(1);
        List<Course> allCoursesByTutorId = courseDAO.getAllByTutorID(user.getId());
        assertEquals(1,allCoursesByTutorId.size());
    }
}