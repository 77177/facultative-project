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

    public void deleteById(int id) {
        courseDAO.deleteById(id);
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
        course.setCourseId(courseDTO.getCourseId());
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

    //getAll methods.
    public List<Course> getAll() {
        return isActiveCheck(courseDAO.getList());
    }

    public List<Course> getAllByUserId(int studentId) {
        return isActiveCheck(courseDAO.getAllByUserID(studentId));
    }

    public List<Course> getAllByTutorID(int id) {
        return isActiveCheck(courseDAO.getAllByTutorID(id));
    }

    private List<Course> isActiveCheck(List<Course> courseList) {
        courseList.forEach(course -> {
            course.setActive(!course.getStartingDate().isBefore(LocalDate.now()));
        });
        return courseList;
    }
}