package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Course;

import java.util.List;

interface CourseDAOInterface extends DAOInterface<Course> {

    List<Course> findAllActive(int pageNumber, int pageSize);

    List<Course> getAllByUserId(int userId, int pageNumber, int pageSize);

}
