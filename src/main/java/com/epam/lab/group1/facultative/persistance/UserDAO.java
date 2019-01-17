package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for creating User entities
 */
@Repository
public class UserDAO {

    private SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> getById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u).where(criteriaBuilder.equal(u.get("id"), id));
        Query<User> queryFinal = session.createQuery(query);
        User user = queryFinal.getSingleResult();
        Optional<User> userOptional = Optional.ofNullable(user);
        return userOptional;
    }

    public Optional<User> getByEmail(String email) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u).where(criteriaBuilder.equal(u.get("email"), email));
        Query<User> queryFinal = session.createQuery(query);
        User user = queryFinal.getSingleResult();
        Optional<User> userOptional = Optional.ofNullable(user);
        return userOptional;
    }

    public User create(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        User userReturn = getByEmail(user.getEmail()).get();
        return userReturn;
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }

    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.load(User.class, id);
        session.getTransaction().commit();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }

    public List<User> getAllStudents() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u).where(criteriaBuilder.equal(u.get("position"), "student"));
        Query<User> query1 = session.createQuery(query);
        List<User> user = query1.getResultList();
        return user;
    }

    public List<User> getAllTutors() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u).where(criteriaBuilder.equal(u.get("position"), "tutor"));
        Query<User> query1 = session.createQuery(query);
        List<User> users = query1.getResultList();
        return users;
    }

    public List<User> getAllStudentByCourseId(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = session.createSQLQuery("SELECT * FROM student_course JOIN users ON student_course.student_id  = users.id WHERE course_id =" + id + " AND position = 'student';").addEntity(User.class).list();
        session.getTransaction().commit();
        return users;
    }

    public void subscribeCourse(int userId, int courseId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.get(User.class, userId);
        session.get(Course.class, courseId);
        Query query = session.createSQLQuery("INSERT INTO student_course(student_id, course_id, mark, feedback)" +
                "VALUES (" + userId + ", " + courseId + ", NULL, 'Empty')") ;
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public void leaveCourse(int userId, int courseId) {
        Session session = sessionFactory.openSession();;
        session.beginTransaction();
        session.get(User.class, userId);
        session.get(Course.class, courseId);
        Query query = session.createSQLQuery("DELETE FROM student_course " +
                "WHERE student_id='" + userId + "' AND course_id='" + courseId + "'");
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
