package com.epam.lab.group1.facultative.view.builder;

import com.epam.lab.group1.facultative.model.Course;
import com.epam.lab.group1.facultative.model.FeedBack;
import com.epam.lab.group1.facultative.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.lab.group1.facultative.view.ViewType.FEEDBACK;

@Component
public class FeedbackViewBuilder {

    private User student = new User();
    private Course course = new Course();
    private FeedBack feedback = new FeedBack();

    public FeedbackViewBuilder() {
    }

    public FeedbackViewBuilder(FeedBack feedback, User student, Course course) {
        this.feedback = feedback;
        this.student = student;
        this.course = course;
    }

    public FeedbackViewBuilder setStudent(User student) {
        this.student = student;
        return this;
    }

    public FeedbackViewBuilder setCourse(Course course) {
        this.course = course;
        return this;
    }

    public FeedbackViewBuilder setFeedBack(FeedBack feedBack) {
        this.feedback = feedBack;
        return this;
    }

    public ModelAndView build() {
        ModelAndView modelAndView = new ModelAndView(FEEDBACK.viewName);
        modelAndView.addObject("course", course);
        modelAndView.addObject("student", student);
        modelAndView.addObject("feedback", feedback);
        return modelAndView;
    }
}
