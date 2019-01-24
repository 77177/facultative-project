package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.FeedBack;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class FeedBackDAO {

    private final Logger logger = Logger.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    public FeedBackDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FeedBack getFeedBack(int courseId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<FeedBack> query = criteriaBuilder.createQuery(FeedBack.class);
            Root<FeedBack> root = query.from(FeedBack.class);
            query.select(root).where(criteriaBuilder.equal(root.get("courseId"), courseId), criteriaBuilder.equal(root.get("studentId"), userId));
            Query<FeedBack> queryFinal = session.createQuery(query);
            FeedBack feedBack = queryFinal.getSingleResult();
            session.getTransaction().commit();
            return feedBack;
        } catch (HibernateException e) {
            String error = String.format("Error during retrieving feedBack by courseId: %d and userId: %d", courseId
                , userId);
            logger.error(error);
            throw e;
        }
    }

    public void saveOrUpdate(FeedBack feedBack) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(feedBack);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            String error = "Error during feedBack persisting. " + feedBack;
            logger.error(error);
            throw e;
        }
    }
}
