package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.CourseDTO;
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

    public Optional<Course> getById(int courseId) {
        return courseDAO.getById(courseId);
    }

    public List<Course> getAllByUserId(int studentId) {
        return courseDAO.getAllByUserID(studentId);
    }

    public List<Course> getAllByTutorID(int id) {
        return courseDAO.getAllByTutorID(id);
    }

    public void deleteById(int id) {
        courseDAO.deleteById(id);
    }

    public List<Course> getAll() {
        return courseDAO.getList();
    }

    public Optional<Course> create(Course course) {
        return courseDAO.create(course);
    }

    public void update(Course course) {
        courseDAO.update(course);
    }

    public Optional<Course> createCourseFromDto(CourseDTO courseDTO) {
        Course course = CourseDtoToCourse(courseDTO);
        return create(course);
    }

    public void updateCourseFromDto(CourseDTO courseDTO) {
        Course course = CourseDtoToCourse(courseDTO);
        update(course);
    }

    private Course CourseDtoToCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setTutorId(courseDTO.getTutorId());
        course.setStartingDate(LocalDate.parse(courseDTO.getStartingDate()));
        course.setFinishingDate(LocalDate.parse(courseDTO.getFinishingDate()));
        course.setActive(courseDTO.isActive());
        return course;
    }

}