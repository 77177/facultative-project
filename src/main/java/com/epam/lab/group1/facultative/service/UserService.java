package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.persistance.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getById(int id) {
        return userDAO.getById(id);
    }

    public User getByEmail(String string) {
        return userDAO.getByEmail(string);
    }

    public User create(User user) {
        return userDAO.create(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    public List<User> getAllStudents() {
        return userDAO.getAllStudents();
    }

    public List<User> getAllTutors() {
        return userDAO.getAllTutors();
    }

    public List<User> getAllStudentByCourseId(int id) {
        return userDAO.getAllStudentByCourseId(id);
    }

    public void leaveCourse(int userId, int courseId) {
        userDAO.leaveCourse(userId, courseId);
    }

    public void subscribeCourse(int userId, int courseId) {
        userDAO.subscribeCourse(userId, courseId);
    }

    public User createUserFromDto(PersonRegistrationFormDTO personRegistrationFormDTO) {
        User user = new User();
        user.setFirstName(personRegistrationFormDTO.getFirstName());
        user.setLastName(personRegistrationFormDTO.getLastName());
        user.setEmail(personRegistrationFormDTO.getEmail());
        user.setPassword(personRegistrationFormDTO.getPassword());
        user.setPosition(personRegistrationFormDTO.getPosition());
        return create(user);
    }
}