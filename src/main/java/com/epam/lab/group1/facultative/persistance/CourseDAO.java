package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAO {

    private final Logger logger = Logger.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    public CourseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Course> getById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Course> optionalCourse = Optional.ofNullable(session.get(Course.class, id));
        session.getTransaction().commit();
        return optionalCourse;
    }

    public Optional<Course> create(Course course) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(course);
        session.getTransaction().commit();
        session.beginTransaction();
        Query<Course> query = session.createSQLQuery("SELECT * FROM courses WHERE courses.course_name = '" + course.getName() + "'").addEntity(Course.class);
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

    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Course course = session.load(Course.class, id);
        session.getTransaction().commit();
        session.beginTransaction();
        session.delete(course);
        session.getTransaction().commit();
    }

    public List<Course> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Course> courses = session.createSQLQuery("SELECT * FROM courses").addEntity(Course.class).list();
        session.getTransaction().commit();
        return courses;
    }

    private List<Course> getAllByUserID(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Course> courses = session.createSQLQuery("SELECT * FROM student_course JOIN courses ON student_course.course_id  = courses.course_id WHERE student_id =" + id + ";").addEntity(Course.class).list();
        session.getTransaction().commit();
        return courses;
    }

    private List<Course> getAllByTutorID(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);
        criteriaQuery.select(courseRoot).where(criteriaBuilder.equal(courseRoot.get("tutorId"), id));
        Query<Course> query1 = session.createQuery(criteriaQuery);
        List<Course> courseList = query1.getResultList();
        return courseList;
    }

    public List<Course> getAllById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u).where(criteriaBuilder.equal(u.get("id"), id));
        Query<User> query1 = session.createQuery(query);
        User user = query1.getSingleResult();
        List<Course> result = null;
        if (user.getPosition().equals("tutor")) {
            result = getAllByTutorID(id);
        } else if (user.getPosition().equals("student")) {
            result = getAllByUserID(id);
        }
        return result;
    }
}