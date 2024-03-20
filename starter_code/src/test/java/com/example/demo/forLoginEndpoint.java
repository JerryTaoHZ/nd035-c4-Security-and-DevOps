package com.example.demo;

import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class forLoginEndpoint {

    private MockMvc mockMvc;

    public forLoginEndpoint(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Test method to test check login of user with correct(happy) and incorrect(unhappy) scenario")
    public void testCreateUserLoginScenario() throws Exception {

        // prepare 参数s
        ObjectMapper mapper = new ObjectMapper();

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("jwttestname1");

        // create a User
        MvcResult result1 = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/user/create")
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists())
                .andReturn();

        // login
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/login")
                                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

//        request.addParameter("Authorization", result.getResponse().getHeader("Authorization"));
//
//        assertNotNull(request.getParameter("Authorization"));

        request.setUsername("DemoInput");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized()).andReturn();

        request.setUsername("DemoInput");

        request.setPassword("DemoPass");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized()).andReturn();

    }

}
