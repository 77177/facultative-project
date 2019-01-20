package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.persistance.FeedBackDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FeedBackService {

    private final Logger logger = Logger.getLogger(this.getClass());
    private FeedBackDAO feedBackDAO;

    public FeedBackService(FeedBackDAO feedBackDAO) {
        this.feedBackDAO = feedBackDAO;
    }

    public FeedBack getFeedBack(int courseId,int userId) {
        return feedBackDAO.getFeedBack(courseId,userId);
    }

    public void saveOrUpdate(FeedBack feedBack) {
        feedBackDAO.saveOrUpdate(feedBack);
    }
}
