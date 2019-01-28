package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.User;

import java.util.List;

public interface UserServiceInterface {
    User getById(int id);

    User getByEmail(String string);

    User create(User user);

    void update(User user);

    void deleteById(int id);

    List<User> getAllStudents();

    List<User> getAllTutors();

    List<User> getAllStudentByCourseId(int id);

    void leaveCourse(int userId, int courseId);

    void subscribeCourse(int userId, int courseId);
}
