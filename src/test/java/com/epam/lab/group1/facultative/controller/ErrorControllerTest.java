package com.epam.lab.group1.facultative.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.epam.lab.group1.facultative.view.ViewType.ERROR;

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
                .andExpect(MockMvcResultMatchers.view().name(ERROR.viewName));
    }

//    @Test
    public void testErrorExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(MockMvcResultMatchers.view().name(ERROR.viewName))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("errorStatus", "errorReason"));
    }
}