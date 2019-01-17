package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.FeedBack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FeedBackDAO {

    private SessionFactory sessionFactory;

    public FeedBackDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FeedBack getFeedBack(int courseId,int userId){
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FeedBack> query = criteriaBuilder.createQuery(FeedBack.class);
        Root<FeedBack> root = query.from(FeedBack.class);
        query.select(root).where(criteriaBuilder.equal(root.get("courseId"),courseId),criteriaBuilder.equal(root.get("studentId"),userId));
        Query<FeedBack> queryFinal = session.createQuery(query);
        List<FeedBack> feedBack = queryFinal.getResultList();
        return feedBack.get(0);
    }

    public void saveOrUpdate(FeedBack feedBack){
        Session session = sessionFactory.openSession();
        session.getTransaction();
        session.save(feedBack);
        session.getTransaction().commit();
    }
}
