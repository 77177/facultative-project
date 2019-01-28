package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.FeedBack;

public interface FeedBackDAOInterface {

    FeedBack getFeedBack(int courseId, int userId);

    void saveOrUpdateFeedBack(FeedBack feedBack);
}
