package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.exception.course.create.CourseCreationEmptyName;
import com.epam.lab.group1.facultative.exception.course.create.CourseCreationWrongDateException;
import com.epam.lab.group1.facultative.exception.course.update.CourseUpdateCourseWithIdDoesNotExistException;
import com.epam.lab.group1.facultative.exception.course.update.CourseUpdateNonUniqueNameException;
import com.epam.lab.group1.facultative.exception.course.update.CourseUpdateWrongDateException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
        if (course.getStartingDate().isBefore(LocalDate.now().plus(1, ChronoUnit.DAYS))) {
            throw new CourseCreationWrongDateException("Course should have starting date at least tomorrow.");
        }
        if (course.getStartingDate().isAfter(course.getFinishingDate().minus(1, ChronoUnit.DAYS))) {
            throw new CourseCreationWrongDateException("Finishing date should be at least 1 day after starting date");
        } else if (course.getName().isEmpty()) {
            throw new CourseCreationEmptyName("Course should have not empty name");
        }
        try {
            course = courseDAO.create(course);
        } catch (PersistenceException e) {
            logger.debug("Course with name " + course.getName() + " already exists in the database. Course name should be unique.");
            throw new CourseUpdateNonUniqueNameException("Course with name " + course.getName() + " already exists in the database. Course name should be unique.");
        }
        return course;
    }

    public void update(Course course) {
        if (course.getStartingDate().isBefore(LocalDate.now().plus(1, ChronoUnit.DAYS))) {
            throw new CourseUpdateWrongDateException("Course should have starting date at least tomorrow.");
        }
        if (course.getStartingDate().isAfter(course.getFinishingDate().minus(1, ChronoUnit.DAYS))) {
            throw new CourseUpdateWrongDateException("Finishing date should be at least 1 day after starting date");
        } else if (course.getName().isEmpty()) {
            throw new CourseCreationEmptyName("Course should have not empty name");
        }
        Course courseFromDB;
        try {
            courseFromDB = courseDAO.getById(course.getId());
        } catch (PersistenceException e) {
            logger.debug("course with id " + course.getId() + " was not found");
            throw new CourseUpdateCourseWithIdDoesNotExistException("course with id " + course.getId() + " was not found");
        }
        if (courseFromDB != null && courseFromDB.getName().equals(course.getName())) {
            courseDAO.update(course);
        } else {
            try {
                courseDAO.getByName(course.getName());
                courseDAO.update(course);
            } catch (PersistenceException e) {
                logger.debug("Course with name " + course.getName() + " already exists in the database. Course name should be unique.");
                throw new CourseUpdateNonUniqueNameException("Course with name " + course.getName() + " already exists in the database. Course name should be unique.");
            }
        }
    }

    public void deleteById(int id) {
        courseDAO.deleteById(id);
    }

    //findAll methods.

    public List<Course> findAll() {
        return courseDAO.findAll().stream().filter(Course::isActive).collect(Collectors.toList());
    }

    public List<Course> getAllByUserId(int userId) {
        return courseDAO.getAllByUserId(userId);
    }
}