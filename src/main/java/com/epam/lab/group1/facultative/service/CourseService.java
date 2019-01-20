package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Course getById(int courseId) {
        return courseDAO.getById(courseId);
    }

    public Course create(Course course) {
        course.setActive(isDateActive(course));
        return courseDAO.create(course);
    }

    public void update(Course course) {
        course.setActive(isDateActive(course));
        courseDAO.update(course);
    }

    public void deleteById(int id) {
        courseDAO.deleteById(id);
    }

    //findAll methods.

    public List<Course> findAll() {
        return isActiveCheck(courseDAO.findAll());
    }

    public boolean isDateActive(Course course) {
        LocalDate today = LocalDate.now();
        boolean state;
        if (today.isBefore(course.getFinishingDate()) && today.isAfter(course.getStartingDate())) {
            state = true;
        } else {
            state = false;
        }
        return state;
    }

    private List<Course> isActiveCheck(List<Course> courseList) {
        courseList.forEach(course -> {
            course.setActive(isDateActive(course));
        });
        return courseList;
    }

    public List<Course> getAllById(int id) {
        return isActiveCheck(courseDAO.getAllById(id));
    }
}