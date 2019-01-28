package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.persistance.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    //TODO check email

    private final Logger logger = Logger.getLogger(this.getClass());
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public User getByEmail(String string) {
        return userDAO.getByEmail(string);
    }

    @Override
    public User create(User user) {
        return userDAO.create(user);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public List<User> getAllStudents() {
        return userDAO.getAllStudents();
    }

    @Override
    public List<User> getAllTutors() {
        return userDAO.getAllTutors();
    }

    @Override
    public List<User> getAllStudentByCourseId(int id) {
        return userDAO.getAllStudentByCourseId(id);
    }

    @Override
    public void leaveCourse(int userId, int courseId) {
        userDAO.leaveCourse(userId, courseId);
    }

    @Override
    public void subscribeCourse(int userId, int courseId) {
        userDAO.subscribeCourse(userId, courseId);
    }
}