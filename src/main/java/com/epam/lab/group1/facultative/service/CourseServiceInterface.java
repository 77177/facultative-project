package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.model.Course;

import java.util.List;

public interface CourseServiceInterface {

    Course getById(int courseId);

    SingleCourseDto create(Course course);

    SingleCourseDto update(Course course);

    boolean deleteById(int courseId);

    List<Course> findAll(int page);

    List<Course> getAllByUserId(int userId, int pageNumber);
}
