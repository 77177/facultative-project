package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.hibernate.PersistentObjectException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/dao/userDaoTestContext.xml")
public class CourseDAOTest {

    @Autowired
    private CourseDAO courseDAO;

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
        Course course = courseDAO.getById(1);
        assertNotNull(course);
        assertEquals("COURSE_1", course.getName());
    }

    @Test
    public void testGetByIdWrongId() {
        Course course = courseDAO.getById(-1);
        assertNull( course);
        course = courseDAO.getById(Integer.MAX_VALUE);
        assertNull( course);
    }

    @Test
    public void testGetAllByUserID() {
        int page = 0;
        int pageSize = 5;
        List<Course> allByUserID = courseDAO.getAllByUserId(3, page, pageSize);
        assertEquals(1, allByUserID.get(0).getId());
        assertEquals("COURSE_1", allByUserID.get(0).getName());
        assertEquals(1, allByUserID.size());
    }

    @Test(expected = NoResultException.class)
    public void testGetAllByUserIDWrongId() {
        int page = 0;
        int pageSize = 5;
        courseDAO.getAllByUserId(-1, page, pageSize);
        courseDAO.getAllByUserId(Integer.MAX_VALUE, page, pageSize);
    }

    @Test
    public void testDeleteById() {
        int page = 1;
        int pageSize = 5;
        courseDAO.deleteById(1);

        assertEquals(1, courseDAO.findAllActive(page, pageSize).size());
        assertEquals(2, courseDAO.findAllActive(page, pageSize).get(0).getId());
        assertEquals("COURSE_2", courseDAO.findAllActive(page, pageSize).get(0).getName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteByIdWrongId() {
        int page = 1;
        int pageSize = 5;
        courseDAO.deleteById(-1);
        assertEquals(0, courseDAO.findAllActive(page, pageSize).size());
    }

    @Test
    public void testGetAllActive() {
        int page = 0;
        int pageSize = 5;
        List<Course> list = courseDAO.findAllActive(page, pageSize);
        assertEquals(2, list.size());
        assertEquals("COURSE_1", list.get(0).getName());
        assertEquals("COURSE_2", list.get(1).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllActiveNegativePage() {
        int page = -1;
        int pageSize = 5;
        courseDAO.findAllActive(page, pageSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllActiveNegativePageSize() {
        int page = 0;
        int pageSize = -5;
        courseDAO.findAllActive(page, pageSize);
    }

    @Test
    public void testCreate() {
        Course course = new Course();
        course.setName("COURSE_3");
        course.setTutorId(1);
        course.setStartingDate(LocalDate.of(1, 1, 1));
        course.setFinishingDate(LocalDate.of(2, 2, 2));
        course.setActive(false);
        courseDAO.create(course);

        assertEquals(course.getName(), courseDAO.getById(course.getId()).getName());
        assertEquals(course.getTutorId(), courseDAO.getById(course.getId()).getTutorId());
        assertEquals(course.getStartingDate(), courseDAO.getById(course.getId()).getStartingDate());
        assertEquals(course.getFinishingDate(), courseDAO.getById(course.getId()).getFinishingDate());
        assertEquals(course.isActive(), courseDAO.getById(course.getId()).isActive());
    }

    @Test(expected = PersistenceException.class)
    public void testCreateNegativeId() {
        Course course = new Course();
        course.setName("COURSE_3");
        course.setId(-1);
        courseDAO.create(course);
    }

    @Test
    public void testUpdate() {
        Course course = courseDAO.getById(1);
        course.setName("New_Course_Name");
        courseDAO.update(course);
        assertEquals("New_Course_Name", courseDAO.getById(1).getName());
    }

    @Test
    public void testUpdateSameName() {
        //TODO kill the test
        Course course1 = courseDAO.getById(1);
        assertNotNull(course1);
        assertEquals(1, course1.getId());
        assertEquals("COURSE_1", course1.getName());

        Course course2 = courseDAO.getById(2);
        assertNotNull(course2);
        assertEquals(2, course2.getId());
        assertEquals("COURSE_2", course2.getName());

        course1.setName("COURSE_2");
        courseDAO.update(course1);

        assertEquals(course1.getName(), course2.getName());
    }

    @Test
    public void testGetAllCourseIdByUserId() {
        int page = 0;
        int pageSize = 5;
        User user = new User();
        user.setId(1);
        user.setPosition("tutor");
        List<Course> allCourseIdbyUserId = courseDAO.getAllByUserId(user.getId(), page, pageSize);
        assertEquals(1, allCourseIdbyUserId.get(0).getId());
        assertEquals("COURSE_1", allCourseIdbyUserId.get(0).getName());
        user.setPosition("student");
        List<Course> allCourseIdbyUserId1 = courseDAO.getAllByUserId(user.getId(), page, pageSize);
        assertEquals(1, allCourseIdbyUserId1.get(0).getId());
        assertEquals("COURSE_1", allCourseIdbyUserId1.get(0).getName());
    }
}