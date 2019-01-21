package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.exception.internal.PersistingEntityException;
import com.epam.lab.group1.facultative.exception.internal.UserWithIdDoesNotExistException;
import com.epam.lab.group1.facultative.model.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {

    private final Logger logger = Logger.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> u = criteriaQuery.from(User.class);
            criteriaQuery.select(u).where(criteriaBuilder.equal(u.get("id"), id));
            User user = session.createQuery(criteriaQuery).getSingleResult();
            Optional<User> optionalUser = Optional.ofNullable(user);
            session.getTransaction().commit();
            return optionalUser.orElseThrow(() -> {
                String error = "user with id " + id + "does not exist in database";
                logger.info(error);
                return new UserWithIdDoesNotExistException(error);
            });
        } catch (PersistenceException e) {
            String error = "Error during user getting by id: " + id;
            logger.error(error);
            throw new UserWithIdDoesNotExistException(error, e);
        }
    }

    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);
            criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("email"), email));
            User user = session.createQuery(criteriaQuery).getSingleResult();
            Optional<User> optionalUser = Optional.ofNullable(user);
            session.getTransaction().commit();
            return optionalUser.orElseThrow(() -> {
                String error = "user with email " + email + "does not exist in database";
                logger.info(error);
                return new UserWithIdDoesNotExistException(error);
            });
        } catch (PersistenceException e) {
            String error = "Error retrieving  user by email: " + email;
            logger.error(error);
            throw new UserWithIdDoesNotExistException(error, e);
        }
    }

    public User create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> u = criteriaQuery.from(User.class);
            criteriaQuery.select(u).where(criteriaBuilder.equal(u.get("email"), user.getEmail()));
            User userReturn = session.createQuery(criteriaQuery).getSingleResult();
            session.getTransaction().commit();
            return userReturn;
        } catch (HibernateException e) {
            String error = "Error during user creating. " + user;
            logger.error(error);
            throw new PersistingEntityException(error, e);
        }
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }

    public void deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException | EntityNotFoundException e) {
            String error = "Error during course deleting by id. " + id;
            logger.error(error);
            throw new PersistingEntityException(error, e);
        }
    }

    public List<User> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
            Root<User> u = query.from(User.class);
            query.select(u).where(criteriaBuilder.equal(u.get("position"), "student"));
            Query<User> query1 = session.createQuery(query);
            List<User> user = query1.getResultList();
            session.getTransaction().commit();
            return user;
        } catch (HibernateException e) {
            String error = "Error during retrieving all users with position 'student'.";
            logger.error(error);
            throw new PersistingEntityException(error, e);
        }
    }

    public List<User> getAllTutors() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
            Root<User> u = query.from(User.class);
            query.select(u).where(criteriaBuilder.equal(u.get("position"), "tutor"));
            Query<User> query1 = session.createQuery(query);
            session.beginTransaction();
            List<User> users = query1.getResultList();
            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            String error = "Error during retrieving all users with position 'tutor'.";
            logger.error(error);
            throw new PersistingEntityException(error, e);
        }
    }

    public List<User> getAllStudentByCourseId(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "SELECT * FROM student_course JOIN users ON student_course.student_id  = users.id " +
                         "WHERE course_id =" + id + " AND position = 'student';";
            List<User> users = session.createSQLQuery(sql).addEntity(User.class).list();
            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            String error = "Error during retrieving all users with position 'student' and by courseId: " + id;
            logger.error(error);
            throw new PersistingEntityException(error, e);
        }
    }

    public void subscribeCourse(int userId, int courseId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = String.format("INSERT INTO student_course(student_id, course_id) VALUES (%d, %d, -1, 'empty')",
                userId, courseId);
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            String error = String.format("Error during subscribing user with id %d, on course with id %d", userId, courseId);
            logger.error(error);
            throw new PersistingEntityException(error, e);
        }
    }

    public void leaveCourse(int userId, int courseId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM student_course " +
                "WHERE student_id='" + userId + "' AND course_id='" + courseId + "'");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            String error = String.format("Error during unsubscribing user with id %d, on course with id %d", userId,
                courseId);
            logger.error(error);
            throw new PersistingEntityException(error, e);
        }
    }
}
