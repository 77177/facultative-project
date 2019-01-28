package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.exception.NotTheCourseTutorException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.persistance.FeedBackDAO;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class FeedBackService implements FeedBackServiceInterface {

    private FeedBackDAO feedBackDAO;
    private CourseService courseService;

    public FeedBackService(FeedBackDAO feedBackDAO, CourseService courseService) {
        this.feedBackDAO = feedBackDAO;
        this.courseService = courseService;
    }

    @Override
    public FeedBack getFeedBack(int courseId, int userId) {

        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Course byId = courseService.getById(courseId);
        if (principal.getUserId() == byId.getTutorId()) {
            return feedBackDAO.getFeedBack(courseId, userId);
        } else {
            throw new NotTheCourseTutorException("you are not eligible to view this info");
        }

    }

    @Override
    public void saveOrUpdateFeedBack(FeedBack feedBack) {
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Course byId = courseService.getById(feedBack.getCourseId());
        if (principal.getUserId() == byId.getTutorId()) {
            feedBackDAO.saveOrUpdateFeedBack(feedBack);
        } else {
            throw new NotTheCourseTutorException("you are not eligible to view this info");
        }
    }
}
