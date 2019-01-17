package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.exception.external.CourseTitleAlreadyExistsException;
import com.epam.lab.group1.facultative.exception.internal.CourseWithIdDoesNotExistException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Course getById(int courseId) {
        Optional<Course> courseById = courseDAO.getById(courseId);
        return courseById.orElseThrow(() -> new CourseWithIdDoesNotExistException("course with id " + courseId + " is not in " +
            "the database"));
    }

    public Course create(Course course) {
        course.setActive(isDateActive(course));
        return courseDAO.create(course).orElseThrow(() -> new CourseTitleAlreadyExistsException("course with title " +
            "already exists"));
    }

    public void update(Course course) {
        course.setActive(isDateActive(course));
        courseDAO.update(course);
    }

    public void deleteById(int id) {
        courseDAO.deleteById(id);
    }

    //getAll methods.

    public List<Course> getAll() {
        return isActiveCheck(courseDAO.findAll());
    }

    public List<Course> getAllByUserId(int id) {
        return isActiveCheck(courseDAO.getAllByUserID(id));
    }

    public List<Course> getAllByTutorID(int id) {
        return isActiveCheck(courseDAO.getAllByTutorID(id));
    }

    private List<Course> isActiveCheck(List<Course> courseList) {
        courseList.forEach(course -> course.setActive(isDateActive(course)));
        return courseList;
    }

    private boolean isDateActive(Course course) {
        LocalDate today = LocalDate.now();
        boolean state;
        if (today.isBefore(course.getFinishingDate()) && today.isAfter(course.getStartingDate())) {
            state = true;
        } else {
            state = false;
        }
        return state;
    }
}