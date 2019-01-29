package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.exception.NotTheCourseTutorException;
import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.persistance.FeedBackDAO;
import com.epam.lab.group1.facultative.persistance.FeedBackDAOInterface;
import com.epam.lab.group1.facultative.security.SecurityContextUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService implements FeedBackServiceInterface {

    private FeedBackDAOInterface feedBackDAO;
    private CourseServiceInterface courseService;
    private UserServiceInterface userService;

    public FeedBackService(FeedBackDAO feedBackDAO, CourseService courseService, UserService userService) {
        this.feedBackDAO = feedBackDAO;
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public FeedBack getFeedBack(int courseId, int userId) {
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Course byId = courseService.getById(courseId);
        List<Integer> allStudentByCourseId = userService.getAllStudentByCourseId(courseId)
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        if (principal.getUserId() == byId.getTutorId() || allStudentByCourseId.contains(userId)) {
            return feedBackDAO.getFeedBack(courseId, userId);
        } else {
            throw new NotTheCourseTutorException("you are not eligible to view this info");
        }

    }

    @Override
    public void saveOrUpdateFeedBack(FeedBack feedBack) {
        SecurityContextUser principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Course byId = courseService.getById(feedBack.getCourseId());
        List<Integer> allStudentByCourseId = userService.getAllStudentByCourseId(feedBack.getCourseId())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        if (principal.getUserId() == byId.getTutorId() || allStudentByCourseId.contains(feedBack.getStudentId())) {
            feedBackDAO.saveOrUpdateFeedBack(feedBack);
        } else {
            throw new NotTheCourseTutorException("you are not eligible to view this info");
        }
    }
}
