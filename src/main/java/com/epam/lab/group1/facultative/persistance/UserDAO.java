package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        Optional<User> optionalUser = Optional.ofNullable(session.load(User.class, email));
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
        List<User> users = session.createSQLQuery("SELECT * FROM users").list();
        session.getTransaction().commit();
        return users;
    }

    public User create(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.beginTransaction();
        User userReturn = session.load(User.class, user.getEmail());
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
