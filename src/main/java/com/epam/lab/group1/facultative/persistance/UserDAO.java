package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

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
        session.beginTransaction();
        Optional<User> optionalUser = Optional.ofNullable(session.load(User.class, id));
        session.getTransaction().commit();
        return optionalUser;
    }

    public Optional<User> getByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery("select u from " + User.class.getName() + " u where u.email = '" + email + "'", User.class);
        Optional<User> optionalUser;
        try {
            optionalUser = Optional.ofNullable(query.getSingleResult());
        } catch (Exception e){
            optionalUser = Optional.empty();
            System.err.println("Not found query, returning NULL");
        }
        session.getTransaction().commit();
        return optionalUser;
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

    public List<User> getList() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = session.createSQLQuery("SELECT * FROM users").addEntity(User.class).list();
        session.getTransaction().commit();
        return users;
    }

    public User create(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.beginTransaction();
        Query<User> query = session.createQuery("select u from " + User.class.getName() + " u where u.email = '" + user.getEmail() + "'", User.class);
        User userReturn = query.getSingleResult();
        session.getTransaction().commit();
        return userReturn;
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }
}
