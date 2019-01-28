package com.epam.lab.group1.facultative.persistance;

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

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration("/dao/userDaoTestContext.xml")
public class FeedBackDAOTest {

    @Before
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("/dao/sql/create_script.sql"),
                new ClassPathResource("/dao/sql/fill_script.sql"));
        populator.execute(this.dataSource);
    }

    @Autowired
    private FeedBackDAO feedBackDAO;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testGetFeedBack() {
        FeedBack feedBack = feedBackDAO.getFeedBack(1, 5);
        assertEquals(5, feedBack.getStudentId());
        assertEquals(1, feedBack.getCourseId());
        assertEquals(-1, feedBack.getMark());
        assertEquals("feed back bad", feedBack.getText());

    }

    @Test
    public void testSaveOrUpdate() {
        FeedBack feedBackIn = new FeedBack();
        feedBackIn.setStudentId(6);
        feedBackIn.setCourseId(2);
        feedBackIn.setMark(4);
        feedBackIn.setText("Good");
        feedBackDAO.saveOrUpdateFeedBack(feedBackIn);
        FeedBack feedBack = feedBackDAO.getFeedBack(2, 6);
        assertEquals(6, feedBack.getStudentId());
        assertEquals(2, feedBack.getCourseId());
        assertEquals(4, feedBack.getMark());
        assertEquals("Good", feedBack.getText());
    }
}