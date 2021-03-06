package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.controller.LocaleHolder;
import com.epam.lab.group1.facultative.dto.SingleCourseDto;
import com.epam.lab.group1.facultative.exception.CourseDoesNotExistException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.persistance.CourseDAO;
import com.epam.lab.group1.facultative.persistance.CourseDAOInterface;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class CourseService implements CourseServiceInterface {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CourseDAOInterface courseDAO;
    private final int pageSize = 10;
    private LocaleHolder localeHolder;

    public CourseService(CourseDAO courseDAO, LocaleHolder localeHolder) {
        this.courseDAO = courseDAO;
        this.localeHolder = localeHolder;
    }

    @Override
    public Course getById(int courseId) {
        Course course = courseDAO.getById(courseId);
        if (course == null) {
            throw new CourseDoesNotExistException("Course was not found in db by id: " + courseId);
        }
        return course;
    }

    /**
     * @param course model from user's view
     * @return SingleCourseDto with info about errors.
     */
    @Override
    public SingleCourseDto create(Course course) {
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        course.setTutorId(principal.getUserId());
        SingleCourseDto singleCourseDto = new SingleCourseDto();
        singleCourseDto.setCourse(course);
        if (checkInputDateCreate(course, singleCourseDto).isErrorPresent()
            || checkNameInCourse(course, singleCourseDto).isErrorPresent()) {
            return singleCourseDto;
        }
        try {
            courseDAO.create(course);
            singleCourseDto.setErrorPresent(false);
        } catch (PersistenceException e) {
            ResourceBundle errorMessages = ResourceBundle.getBundle("bundle.errorMessages", localeHolder.getLocale());
            String message = String.format(errorMessages.getString("courseWithNameAlreadyExists"), course.getName());
            logger.debug(message, e);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return singleCourseDto;
    }

    /**
     * @param course model from user's view
     * @return SingleCourseDto with info about errors.
     */
    @Override
    public SingleCourseDto update(Course course) {
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        course.setTutorId(principal.getUserId());
        SingleCourseDto singleCourseDto = new SingleCourseDto();
        singleCourseDto.setCourse(course);
        if (checkInputDateUpdate(course, singleCourseDto).isErrorPresent()
            || checkNameInCourse(course, singleCourseDto).isErrorPresent()) {
            return singleCourseDto;
        }
        try {
            if (principal.getUserId() == courseDAO.getById(course.getId()).getTutorId()) {
                courseDAO.update(course);
                singleCourseDto.setErrorPresent(false);
            } else {
                String message = String.format("Tutor with id: %s" + " tried to update the course with id: %s", principal.getUserId(), course.getId());
                logger.debug(message);
                singleCourseDto.setErrorPresent(true);
            }
        } catch (PersistenceException e) {
            ResourceBundle errorMessages = ResourceBundle.getBundle("bundle.errorMessages", localeHolder.getLocale());
            String message = String.format(errorMessages.getString("courseWithNameAlreadyExists"), course.getName());
            logger.debug(message, e);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return singleCourseDto;

    }

    @Override
    public boolean deleteById(int courseId) {
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getUserId() == courseDAO.getById(courseId).getTutorId()) {
            courseDAO.deleteById(courseId);
            return true;
        } else {
            String message = String.format("Tutor with id: %s" + " tried to delete the course with id: %s", principal.getUserId(), courseId);
            logger.debug(message);
            return false;
        }
    }

    @Override
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

    @Override
    public List<Course> getAllByUserId(int userId, int pageNumber) {
        return courseDAO.getAllByUserId(userId, pageNumber, pageSize);
    }

    private SingleCourseDto checkInputDateUpdate(Course course, SingleCourseDto singleCourseDto) {
        if (checkNullDate(course, singleCourseDto).isErrorPresent()) {
            return singleCourseDto;
        }
        return checkWrongStartDate(course, singleCourseDto);
    }

    private SingleCourseDto checkInputDateCreate(Course course, SingleCourseDto singleCourseDto) {
        if (checkNullDate(course, singleCourseDto).isErrorPresent()) {
            return singleCourseDto;
        }
        if (course.getStartingDate().isBefore(LocalDate.now().plus(1, ChronoUnit.DAYS))) {
            ResourceBundle errorMessages = ResourceBundle.getBundle("bundle.errorMessages", localeHolder.getLocale());
            String message = String.format(errorMessages.getString("wrongStartFinishDate"),
                course.getStartingDate(), course.getFinishingDate());
            logger.debug(message);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return checkWrongStartDate(course, singleCourseDto);
    }

    private SingleCourseDto checkWrongStartDate(Course course, SingleCourseDto singleCourseDto) {
        if (course.getStartingDate().isAfter(course.getFinishingDate())) {
            ResourceBundle errorMessages = ResourceBundle.getBundle("bundle.errorMessages", localeHolder.getLocale());
            String message = String.format(errorMessages.getString("wrongStartDate"),
                course.getStartingDate(), course.getFinishingDate());
            logger.debug(message);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return singleCourseDto;
    }

    private SingleCourseDto checkNullDate(Course course, SingleCourseDto singleCourseDto) {
        if (course.getFinishingDate() == null || course.getStartingDate() == null) {
            String message = "Date can not be null";
            logger.debug(message);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return singleCourseDto;
    }

    private SingleCourseDto checkNameInCourse(Course course, SingleCourseDto singleCourseDto) {
        if (course.getName().isEmpty()) {
            ResourceBundle errorMessages = ResourceBundle.getBundle("bundle.errorMessages", localeHolder.getLocale());
            String message = errorMessages.getString("emptyName");
            logger.debug(message);
            singleCourseDto.setErrorPresent(true);
            singleCourseDto.setErrorMessage(message);
        }
        return singleCourseDto;
    }

}