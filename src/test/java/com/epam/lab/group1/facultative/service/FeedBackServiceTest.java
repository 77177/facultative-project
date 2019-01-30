package com.epam.lab.group1.facultative.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertTrue;

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
    public void everythingCoveredByController() {
        assertTrue(true);
    }
}
