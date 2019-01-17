package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.exception.external.EmailAlreadyExistsException;
import com.epam.lab.group1.facultative.exception.internal.UserWithEmailDoesNOtExistException;
import com.epam.lab.group1.facultative.exception.internal.UserWithIdDoesNotExistException;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.persistance.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getById(int userId) {
        return userDAO.getById(userId).orElseThrow(() -> new UserWithIdDoesNotExistException("user with id " + userId + " was not " +
            "found in the database"));
    }

    public User getByEmail(String email) {
        return userDAO.getByEmail(email).orElseThrow(() -> new UserWithEmailDoesNOtExistException("user with email " + email + " already exists"));
    }

    public User create(User user) {
        userDAO.getById(user.getId())
                .ifPresent(user1 -> new EmailAlreadyExistsException("user with this email already exists"));
        return userDAO.create(user);
    }

    public void update(User user) {
        userDAO.getByEmail(user.getEmail())
                .ifPresent(user1 -> new EmailAlreadyExistsException("Email already exists"));
        userDAO.update(user);
    }

    public void deleteById(int userId) {
        userDAO.getById(userId).orElseThrow(() -> new UserWithIdDoesNotExistException("user with id " + userId + " was not " +
            "found in the database"));
        userDAO.deleteById(userDAO.getById(userId).orElseThrow(UserWithIdDoesNotExistException::new).getId());
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
        userDAO.getById(userId).orElseThrow(() -> new UserWithIdDoesNotExistException("user with id " + userId + " was not " +
            "found in the database"));
        userDAO.leaveCourse(userId, courseId);
    }

    public void subscribeCourse(int userId, int courseId) {
        userDAO.getById(userId).orElseThrow(() -> new UserWithIdDoesNotExistException("user with id " + userId + " was not " +
            "found in the database"));
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