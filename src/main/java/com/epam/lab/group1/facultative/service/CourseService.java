package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.exception.CourseDoesNotExistException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CourseService {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CourseDAO courseDAO;
    private final int pageSize = 10;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Course getById(int courseId) {
        Course course = courseDAO.getById(courseId);
        if (course == null) {
            throw new CourseDoesNotExistException("Course was not found in db by id: " + courseId);
        }
        return courseDAO.getById(courseId);
    }

    public SingleCourseDto create(Course course) {
        SingleCourseDto singleCourseDto = new SingleCourseDto();
        singleCourseDto.setCourse(course);
        checkInputDataAndName(course, singleCourseDto);
        if (singleCourseDto.isErrorPresent()) {
            return singleCourseDto;
        }

        try {
            courseDAO.create(course);
            singleCourseDto.setErrorPresent(false);
        } catch (ConstraintViolationException e) {
            String message = String.format("Course was not created. Probably with name %s already exists. Course: %s",
                    course.getName(), course.getName());
            logger.debug(message, e);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return singleCourseDto;
    }

    public SingleCourseDto update(Course course) {
        SingleCourseDto singleCourseDto = new SingleCourseDto();
        singleCourseDto.setCourse(course);
        checkNameInCourse(course, singleCourseDto);
        if (singleCourseDto.isErrorPresent()) {
            return singleCourseDto;
        }

        try {
            courseDAO.update(course);
            singleCourseDto.setErrorPresent(false);
        } catch (ConstraintViolationException e) {
            String message = String.format("Course was not updated. Probably with name %s already exists. Course: %s",
                    course.getName(), course.getName());
            logger.debug(message, e);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return singleCourseDto;
    }

    public void deleteById(int id) {
        courseDAO.deleteById(id);
    }

    public List<Course> findAll(int page) {
        if (page < 0) {
            page = 0;
        }
        List<Course> courseList = courseDAO.findAllActive(page, pageSize);
        if (courseList.size() == 0) {
            courseList = courseDAO.findAllActive(page - 1, pageSize);
        }
        return courseList;
    }

    public List<Course> getAllByUserId(int userId, int pageNumber) {
        return courseDAO.getAllByUserId(userId, pageNumber, pageSize);
    }

    private SingleCourseDto checkInputDataAndName(Course course, SingleCourseDto singleCourseDto) {
        if (course.getStartingDate().isBefore(LocalDate.now().plus(1, ChronoUnit.DAYS))) {
            String message = String.format("Wrong input date for course. Course should have starting date at least tomorrow." +
                            "Course start date: %s Minimum date is: %s",
                    course.getStartingDate().toString(), LocalDate.now().plus(2, ChronoUnit.DAYS).toString());
            logger.debug(message);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        } else if (course.getStartingDate().isAfter(course.getFinishingDate().minus(1, ChronoUnit.DAYS))) {
            String message = String.format("Wrong input date for course. Finishing date should be at least 1 day after starting date." +
                            "Start date: %s Finishing date is: %s",
                    course.getStartingDate().toString(), course.getFinishingDate().toString());
            logger.debug(message);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        } else checkNameInCourse(course, singleCourseDto);
        return singleCourseDto;
    }

    private void checkNameInCourse(Course course, SingleCourseDto singleCourseDto) {
        if (course.getName().isEmpty()) {
            String message = "Wrong input name for course. Course should have not empty name.";
            logger.debug(message);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
    }
}