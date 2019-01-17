package com.epam.lab.group1.facultative.persistance;

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
        Query<User> query1 = session.createQuery(query);
        User user;
        Optional<User> optionalUser;
        try {
            user = query1.getSingleResult();
            optionalUser = Optional.ofNullable(user);
        } catch (Exception e) {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }

    public Optional<User> getByEmail(String email) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u).where(criteriaBuilder.equal(u.get("email"), email));
        Query<User> query1 = session.createQuery(query);
        User user;
        Optional<User> optionalUser;
        try {
            user = query1.getSingleResult();
            optionalUser = Optional.ofNullable(user);
        } catch (Exception e) {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }

    public User create(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u).where(criteriaBuilder.equal(u.get("email"), user.getEmail()));
        Query<User> query1 = session.createQuery(query);
        User userReturn = query1.getSingleResult();
        session.getTransaction().commit();
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
        query.select(u);
        Query<User> query1 = session.createQuery(query);
        session.beginTransaction();
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
}
