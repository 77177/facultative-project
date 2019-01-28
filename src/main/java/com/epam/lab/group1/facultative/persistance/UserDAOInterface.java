package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.User;

import java.util.List;

public interface UserDAOInterface extends DAOInterface<User> {

    List<User> getAllStudents();

    List<User> getAllTutors();

    List<User> getAllStudentByCourseId(int id);

    void subscribeCourse(int userId, int courseId);

    void leaveCourse(int userId, int courseId);

}
