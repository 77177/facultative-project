package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String sql;
    private JdbcTemplate jdbcTemplate;

    public CourseDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Integer> getAllCourseIdbyUserId(User user) {
        List<Integer> integers = Collections.emptyList();
        if (user.getPosition().equals("tutor")) {integers = jdbcTemplate.queryForList("SELECT course_id FROM courses where tutor_id = " + user.getId() + ";"
                    , Integer.class);
        } else {
            integers = jdbcTemplate.queryForList("SELECT course_id FROM student_course where student_id = " + user.getId() + ";"
                    , Integer.class);
        }
        return integers;
    }

    public Optional<Course> getById(int id) {
        sql = String.format("SELECT * FROM courses WHERE course_id = %d;", id);
        Course course = null;
        try {
            course = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Course.class));
        } finally {
            return Optional.ofNullable(course);
        }
    }

    public List<Course> getAllByTutorID(int id) {
        String sql = "SELECT * FROM courses WHERE tutor_id =" + id + ";";
        List<Course> courses = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class));
        return courses;
    }

    public List<Course> getAllByUserID(int id) {
        String sql = "SELECT * FROM student_course JOIN courses ON student_course.course_id  = courses.course_id WHERE student_id =" + id + ";";
        List<Course> courses = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class));
        return courses;
    }

    public void deleteById(int id) {
        sql = String.format("DELETE FROM courses WHERE course_id = %d;", id);
        jdbcTemplate.execute(sql);
    }

    public List<Course> getList() {
        sql = "SELECT * FROM courses";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class));
    }

    public Optional<Course> create(Course course) {
        sql = "INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active) VALUES(:courseName,:tutorId,:startingDate,:finishingDate,:active);";
        MapSqlParameterSource sqlParameterSource;
        sqlParameterSource = new MapSqlParameterSource("courseName", course.getCourseName());
        createMap(course, sqlParameterSource);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);

        Optional<Course> course1 = null;
        course1 = Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM courses WHERE course_name = ?;"
                , new BeanPropertyRowMapper<>(Course.class), course.getCourseName()));
        return course1;
    }

    public void update(Course course) {
        sql = "UPDATE courses SET course_name=:courseName, tutor_id=:tutorId, starting_date=:startingDate, finishing_date=:finishingDate, active=:active WHERE course_id = :id ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseName", course.getCourseName());
        createMap(course, sqlParameterSource);
        sqlParameterSource.addValue("id", course.getCourseId());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    private void createMap(Course course, MapSqlParameterSource sqlParameterSource) {
        sqlParameterSource.addValue("tutorId", course.getTutorId());
        sqlParameterSource.addValue("startingDate", course.getStartingDate());
        sqlParameterSource.addValue("finishingDate", course.getFinishingDate());
        sqlParameterSource.addValue("active", course.isActive());
    }
}