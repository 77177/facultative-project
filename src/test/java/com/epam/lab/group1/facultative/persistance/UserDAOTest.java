package com.epam.lab.group1.facultative.persistance;

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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/dao/userDaoTestContext.xml")
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

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
    public void testGetAllStudents() {
        List<User> allStudents = userDAO.getAllStudents();
        assertNotNull(allStudents);
        assertEquals(5, allStudents.size());
    }

    @Test
    public void testGetAllTutors() {
        List<User> allTutors = userDAO.getAllTutors();
        assertNotNull(allTutors);
        assertEquals(4, allTutors.size());
    }

    @Test
    public void testGetById() {
        int existingUserId = 1;
        User user = userDAO.getById(existingUserId);
        assertNotNull(user);
        assertEquals("Mark", user.getFirstName());
        assertEquals("Rasane", user.getLastName());
        assertEquals("0tutor@gmail.com", user.getEmail());
        assertEquals("$2a$05$1MRWimua526pMo7b7c0ynuxzjBQGkW/nNGD2CfQ7m6UUsNda5Do/m", user.getPassword());
        assertEquals("tutor", user.getPosition());
    }

    @Test(expected = PersistenceException.class)
    public void testGetByIdWrongId() {
        int nonExistingUserId = Integer.MAX_VALUE;
        userDAO.getById(nonExistingUserId);

    }

    @Test
    public void testGetByEmail() {
        String existingUserEmail = "1student@gmail.com";
        User user = userDAO.getByEmail(existingUserEmail);
        assertNotNull(user);
        assertEquals("Sam", user.getFirstName());
        assertEquals("Garrison", user.getLastName());
        assertEquals("1student@gmail.com", user.getEmail());
        assertEquals("$2a$05$GYIJLW9IhhmzCqOtMlAxZOhDCHxWEA3YD/xwb5tTD1VAk2l1dAOSe", user.getPassword());
        assertEquals("student", user.getPosition());
    }

    @Test(expected = PersistenceException.class)
    public void testGetByEmailWrongEmail() {
        String nonExistingUserEmail = "";
        userDAO.getByEmail(nonExistingUserEmail);
    }

    @Test
    public void testUpdate() {
        int id = 1;
        String firstName = "Doctor";
        String lastName = "Ai-bolit";
        String email = "dobryi.doctor@podderevom.ru";
        String password = "menyaUkusilaOsa";
        String position = "patient";

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPosition(position);
        userDAO.update(user);

        user = userDAO.getById(id);
        assertNotNull(user);
        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(position, user.getPosition());

        user = userDAO.getByEmail(email);
        assertNotNull(user);
        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(position, user.getPosition());
    }

    @Test(expected = PersistenceException.class)
    public void testDeleteById() {
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("email");
        user.setPosition("position");
        User dbUser = userDAO.create(user);
        assertEquals(10, dbUser.getId());
        userDAO.deleteById(10);
        userDAO.deleteById(10);
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setEmail("anithing@something.com");
        userDAO.create(user);
        User userFromDB = userDAO.getByEmail(user.getEmail());
        assertEquals(user.getFirstName(), userFromDB.getFirstName());
        assertEquals(user.getLastName(), userFromDB.getLastName());
        assertEquals(user.getEmail(), userFromDB.getEmail());
        assertEquals(user.getPassword(), userFromDB.getPassword());
        assertEquals(user.getPosition(), userFromDB.getPosition());
    }

    @Test
    public void testGetAllStudentByCourseId() {
        int courseId = 1;
        List<User> allStudentByCourseId = userDAO.getAllStudentByCourseId(courseId);
        assertEquals(5, allStudentByCourseId.size());
        assertEquals("Laura", allStudentByCourseId.get(0).getFirstName());
        assertEquals("Hieme", allStudentByCourseId.get(0).getLastName());
        assertEquals("Sam", allStudentByCourseId.get(1).getFirstName());
        assertEquals("Garrison", allStudentByCourseId.get(1).getLastName());
    }

    @Test
    public void getAllStudents() {
        List<User> allStudents = userDAO.getAllStudents();
        assertEquals(5, allStudents.size());
        assertEquals("Laura", allStudents.get(0).getFirstName());
        assertEquals("Hieme", allStudents.get(0).getLastName());
        assertEquals("Sam", allStudents.get(1).getFirstName());
        assertEquals("Garrison", allStudents.get(1).getLastName());
        assertEquals("Donald", allStudents.get(2).getFirstName());
        assertEquals("Trump", allStudents.get(2).getLastName());
    }
}