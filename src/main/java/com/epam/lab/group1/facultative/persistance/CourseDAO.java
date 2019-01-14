package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String sql;
    private JdbcTemplate jdbcTemplate;
    private SessionFactory sessionFactory;


    public CourseDAO(DataSource dataSource, SessionFactory sessionFactory) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sessionFactory = sessionFactory;
    }

    public List<Integer> getAllCourseIdbyUserId(User user) {
        Session session = sessionFactory.openSession();
        List<Course> courses = Collections.emptyList();
        session.beginTransaction();
        if (user.getPosition().equals("tutor")) {
            courses = session.createSQLQuery("SELECT * FROM courses WHERE tutor_id = " + user.getId() + ";").addEntity(Course.class).getResultList();
        } else {
            courses = session.createSQLQuery("SELECT * FROM student_course join courses on student_course.course_id = courses.course_id where student_id = " + user.getId() + ";").addEntity(Course.class).getResultList();
        }
        session.getTransaction().commit();

        List<Integer> courseIds = new ArrayList<>();
        courses.forEach(c->courseIds.add(c.getCourseId()));
        return courseIds;
    }

    public Optional<Course> getById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Course> optionalCourse = Optional.ofNullable(session.get(Course.class, id));
        session.getTransaction().commit();
        return optionalCourse;
    }

    public List<Course> getAllByUserID(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Course> courses = session.createSQLQuery("SELECT * FROM student_course JOIN courses ON student_course.course_id  = courses.course_id WHERE student_id =" + id + ";").addEntity(Course.class).list();
        session.getTransaction().commit();
        return courses;
    }

    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Course course = session.load(Course.class, id);
        session.getTransaction().commit();
        session.beginTransaction();
        session.delete(course);
        session.getTransaction().commit();
    }

    public List<Course> getList() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Course> courses = session.createSQLQuery("SELECT * FROM courses").addEntity(Course.class).list();
        session.getTransaction().commit();
        return courses;
    }

    public List<Course> getAllByTutorID(int id) {
        String sql = "SELECT * FROM courses WHERE tutor_id =" + id + ";";
        List<Course> courses = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class));
        return courses;
    }

    public Optional<Course> create(Course course) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(course);
        session.getTransaction().commit();
        session.beginTransaction();
        Query<Course> query = session.createSQLQuery("SELECT * FROM courses WHERE courses.course_name = '" + course.getCourseName()+"'").addEntity(Course.class);
        Course courseReturn = query.getSingleResult();
        Optional<Course> optionalCourse = Optional.ofNullable(courseReturn);
        session.getTransaction().commit();
        return optionalCourse;
    }

    public void update(Course course) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(course);
        session.getTransaction().commit();
    }

    private void createMap(Course course, MapSqlParameterSource sqlParameterSource) {
        sqlParameterSource.addValue("tutorId", course.getTutorId());
        sqlParameterSource.addValue("startingDate", course.getStartingDate());
        sqlParameterSource.addValue("finishingDate", course.getFinishingDate());
        sqlParameterSource.addValue("active", course.isActive());
    }
    
}