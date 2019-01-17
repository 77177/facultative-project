package com.epam.lab.group1.facultative.persistance;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FeedBackDao {

    private SessionFactory sessionFactory;

    public FeedBackDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
