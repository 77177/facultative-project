package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.FeedBack;

public interface FeedBackServiceInterface {
    FeedBack getFeedBack(int courseId, int userId);

    void saveOrUpdateFeedBack(FeedBack feedBack);
}
