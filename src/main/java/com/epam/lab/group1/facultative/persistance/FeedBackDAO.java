package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.FeedBack;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FeedBackDAO {

    private final Logger logger = Logger.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    public FeedBackDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FeedBack getFeedBack(int courseId, int userId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FeedBack> query = criteriaBuilder.createQuery(FeedBack.class);
        Root<FeedBack> root = query.from(FeedBack.class);
        query.select(root).where(criteriaBuilder.equal(root.get("courseId"), courseId), criteriaBuilder.equal(root.get("studentId"), userId));
        Query<FeedBack> queryFinal = session.createQuery(query);
        FeedBack feedBack = queryFinal.getSingleResult();
        return feedBack;
    }

    public void saveOrUpdate(FeedBack feedBack) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.saveOrUpdate(feedBack);
        session.getTransaction().commit();
    }
}
