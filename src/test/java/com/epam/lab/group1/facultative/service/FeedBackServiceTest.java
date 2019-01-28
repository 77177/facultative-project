package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.model.FeedBack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration("/service/feedBackServiceTestContext.xml")
public class FeedBackServiceTest {

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private DataSource dataSource;

    @Before
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("/dao/sql/create_script.sql"),
                new ClassPathResource("/dao/sql/fill_script.sql"));
        populator.execute(this.dataSource);
    }

    @Test
    public void testGetFeedBack() {
        FeedBack feedBack = feedBackService.getFeedBack(1, 5);
        assertEquals(5, feedBack.getStudentId());
        assertEquals(1, feedBack.getCourseId());
        assertEquals(-1, feedBack.getMark());
        assertEquals("feed back bad", feedBack.getText());
    }

    @Test
    public void testSaveOrUpdate() {
        FeedBack feedBackIn = new FeedBack();
        feedBackIn.setStudentId(4);
        feedBackIn.setCourseId(2);
        feedBackIn.setMark(4);
        feedBackIn.setText("Good");
        feedBackService.saveOrUpdateFeedBack(feedBackIn);
        FeedBack feedBack = feedBackService.getFeedBack(2, 4);
        assertEquals(4, feedBack.getStudentId());
        assertEquals(2, feedBack.getCourseId());
        assertEquals(4, feedBack.getMark());
        assertEquals("Good", feedBack.getText());
    }
}
