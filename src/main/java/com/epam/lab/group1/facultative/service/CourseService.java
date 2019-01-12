package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Optional<Course> getById(int courseId) {
        return courseDAO.getById(courseId);
    }

    public List<Course> getByTutorId(int tutorId) {
        return courseDAO.getByTutorId(tutorId);
    }

    public List<Course> getByStudentId(int studentId) {
        return courseDAO.getByStudentId(studentId);
    }

    public List<User> getStudentList(int id) {
        return courseDAO.getStudentList(id);
    }

    public void deleteById(int id) {
        courseDAO.deleteById(id);
    }

    public List<Course> getAll() {
        return courseDAO.getList();
    }

    public Course create(Course course) {
        return courseDAO.create(course);
    }


    public void update(Course course) {
        courseDAO.update(course);
    }
}