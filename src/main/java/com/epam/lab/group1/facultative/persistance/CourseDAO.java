package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CourseDAO {

    private final Logger logger = Logger.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    public CourseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Course getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Course course = session.get(Course.class, id);
            session.getTransaction().commit();
            return course;
        } catch (PersistenceException e) {
            logger.info("course with id " + id + "does not exist in database");
            throw e;
        }
    }

    public Course getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
            Root<Course> userRoot = criteriaQuery.from(Course.class);
            criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("name"), name));
            Course course = session.createQuery(criteriaQuery).getSingleResult();
            session.getTransaction().commit();
            return course;
        } catch (PersistenceException e) {
            String error = "Error retrieving course by name: " + name;
            logger.error(error);
            throw e;
        }
    }

    public Course create(Course course) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.persist(course);
                session.getTransaction().commit();
            } catch (PersistenceException e) {
                String error = "Error during course persisting. " + course;
                logger.error(error);
                throw e;
            }

            session.beginTransaction();
            String sql = "SELECT * FROM courses WHERE courses.course_name = '" + course.getName() + "'";
            Query<Course> query = session.createSQLQuery(sql).addEntity(Course.class);
            Course courseReturn = query.getSingleResult();
            session.getTransaction().commit();
            return courseReturn;
        } catch (PersistenceException e) {
            logger.error("course with name " + course.getName() + "does not exist in database");
            throw e;
        }
    }

    public void update(Course course) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            String error = "Error during course updating. " + course;
            logger.error(error);
            throw e;
        }
    }

    public void deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Course course = session.load(Course.class, id);
            session.delete(course);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            String error = "Error during deleting by courseID: " + id;
            logger.error(error);
            throw e;
        }
    }

    public List<Course> findAll(int pageNumber, int pageSize) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

            Root<Course> courseRoot = criteriaQuery.from(Course.class);
            criteriaQuery
                .select(courseRoot)
                .where(criteriaBuilder.equal(courseRoot.get("active"), true));

            List<Course> resultList = session
                .createQuery(criteriaQuery)
                .setFirstResult(pageNumber * pageSize)
                .setMaxResults(pageSize)
                .getResultList();


            session.getTransaction().commit();
            return resultList;
        } catch (PersistenceException e) {
            String error = "Error during retrieving all courses.";
            logger.error(error);
            throw e;
        }
    }

    private List<Course> getAllByUserID(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "SELECT * FROM student_course JOIN courses ON student_course.course_id  = courses.course_id " +
                         "WHERE student_id =" + id + ";";
            List<Course> courses = session.createSQLQuery(sql).addEntity(Course.class).list();
            session.getTransaction().commit();
            return courses;
        } catch (PersistenceException e) {
            String error = "Error during retrieving all courses by user id: " + id;
            logger.error(error);
            throw e;
        }
    }

    private List<Course> getAllByTutorID(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
            Root<Course> courseRoot = criteriaQuery.from(Course.class);
            criteriaQuery.select(courseRoot).where(criteriaBuilder.equal(courseRoot.get("tutorId"), id));
            Query<Course> query1 = session.createQuery(criteriaQuery);
            List<Course> courseList = query1.getResultList();
            session.getTransaction().commit();
            return courseList;
        } catch (PersistenceException e) {
            String error = "Error during retrieving all courses by tutor id: " + id;
            logger.error(error);
            throw e;
        }
    }

    public List<Course> getAllByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
            Root<User> u = query.from(User.class);
            query.select(u).where(criteriaBuilder.equal(u.get("id"), userId));
            Query<User> query1 = session.createQuery(query);
            User user = query1.getSingleResult();
            List<Course> result = null;
            session.getTransaction().commit();
            if (user.getPosition().equals("tutor")) {
                result = getAllByTutorID(userId);
            } else if (user.getPosition().equals("student")) {
                result = getAllByUserID(userId);
            }
            return result;
        } catch (PersistenceException e) {
            String error = "Error during retrieving all courses by user id: " + userId;
            logger.error(error);
            throw e;
        }
    }
}