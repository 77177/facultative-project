package com.epam.lab.group1.facultative.controller;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("/controller/errorControllerTestContext.xml")
public class ErrorControllerTest {

    private MockMvc mockMvc;

    public ErrorControllerTest() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ErrorController())
                .build();
    }

    @Test
    public void testErrorNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(MockMvcResultMatchers.view().name(ERROR));
    }

//    @Test
    public void testErrorExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(MockMvcResultMatchers.view().name(ERROR))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("errorStatus", "errorReason"));
    }
}