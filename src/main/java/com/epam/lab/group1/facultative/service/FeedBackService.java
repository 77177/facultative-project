package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.persistance.FeedBackDAO;

public class FeedBackService {

    private FeedBackDAO feedBackDAO;

    public FeedBackService(FeedBackDAO feedBackDAO) {
        this.feedBackDAO = feedBackDAO;
    }

    public FeedBack getFeedBack(int coruseId,int userId) {
        return feedBackDAO.getFeedBack(coruseId,userId);
    }

    public void saveOrUpdate(FeedBack feedBack) {
        feedBackDAO.saveOrUpdate(feedBack);
    }
}
