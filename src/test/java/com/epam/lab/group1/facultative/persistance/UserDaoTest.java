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

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/userDaoTest/UserDaoSpringContextConfiguration.xml")
public class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

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
    public void testGetList() {
        List<User> allUsers = userDAO.getList();
        assertNotNull(allUsers);
        assertEquals(7, allUsers.size());
    }

    @Test
    public void testGetById() {
        int existingUserId = 1;
        Optional<User> optionalUser = userDAO.getById(existingUserId);
        assertNotNull(optionalUser);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertNotNull(user);
        assertEquals("Mark", user.getFirstName());
        assertEquals("Rasane", user.getLastName());
        assertEquals("0tutor@gmail.com", user.getEmail());
        assertEquals("0", user.getPassword());
        assertEquals("tutor", user.getPosition());

        int nonExistingUserId = Integer.MAX_VALUE;
        optionalUser = userDAO.getById(nonExistingUserId);
        assertNotNull(optionalUser);
    }

    @Test
    public void testGetByEmail() {
        String existingUserEmail = "1student@gmail.com";
        Optional<User> optionalUser = userDAO.getByEmail(existingUserEmail);
        assertNotNull(optionalUser);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertNotNull(user);
        assertEquals("Sam", user.getFirstName());
        assertEquals("Garrison", user.getLastName());
        assertEquals("1student@gmail.com", user.getEmail());
        assertEquals("1", user.getPassword());
        assertEquals("student", user.getPosition());

        String nonExistingUserEmail = "";
        optionalUser = userDAO.getByEmail(nonExistingUserEmail);
        assertNotNull(optionalUser);
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

        Optional<User> optionalUser = userDAO.getById(id);
        assertNotNull(optionalUser);
        assertTrue(optionalUser.isPresent());
        user = optionalUser.get();
        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(position, user.getPosition());

        optionalUser = userDAO.getByEmail(email);
        assertNotNull(optionalUser);
        assertTrue(optionalUser.isPresent());
        user = optionalUser.get();
        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(position, user.getPosition());
    }

    @Test
    public void testDeleteById() {
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("email");
        user.setPosition("position");
        userDAO.getList().forEach(user1 -> System.out.println(user1));
        userDAO.create(user);
        user.setEmail("asd");
        userDAO.create(user);
        userDAO.getList().forEach(user1 -> System.out.println(user1));
        assertEquals(8, userDAO.getList().size());
        userDAO.deleteById(8);
        userDAO.getList().forEach(user1 -> System.out.println(user1));
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setEmail("anithing@something.com");
        userDAO.create(user);
        assertTrue(true);
    }
}