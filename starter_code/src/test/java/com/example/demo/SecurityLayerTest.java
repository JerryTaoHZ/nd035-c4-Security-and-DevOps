package com.example.demo;

import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityLayerTest {

    @Autowired
    private MockMvc mockMvc;
    private String bearer;

    @Test
    public void ForbiddenWhenUnlogin() throws Exception {
        mockMvc.perform(get("/api/user/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    public void createUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CreateUserRequest userReq = new CreateUserRequest("Micky", "password", "password");

        // create a User
        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsBytes(userReq)))
                        .content(mapper.writeValueAsString(userReq)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username").value("Micky"))
                .andExpect(jsonPath("$.id").value(2));
    }

//    @Test
//    public void createUser1() throws Exception{
//        ObjectMapper mapper = new ObjectMapper();
//        CreateUserRequest userReq = new CreateUserRequest("Jerry", "password", "password");
//
//        // create a User
//        try {
//            mockMvc.perform(post("api/user/create")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(mapper.writeValueAsString(userReq)))
//                    .andExpect(status().isOk())
//                    .andDo(print())
//                    .andExpect(jsonPath("$.username").value("Jerry"));
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Test
    public void LoginAndAuthorizedAction() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CreateUserRequest userReq = new CreateUserRequest("Jerry", "password", "password");

        // create a User
        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(userReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Jerry"))
                .andExpect(jsonPath("$.id").value(1));

//        String reqBody = "{\"username\":\"Jerry\",\"password\":\"password\"}";
        UserLoginInfo userLoginInfo = new UserLoginInfo("Jerry", "password");

        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(userLoginInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andDo(print())
                .andReturn();

//        String bearer = result.getResponse().getHeader("Authorization");
        String bearer = result.getResponse().getHeader(HttpHeaders.AUTHORIZATION);

        MvcResult mvcResult = mockMvc.perform(get("/api/user/" + userLoginInfo.getUsername())
                        .header("Authorization", bearer)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(content().json("{'id':'1','username':'Jerry'}"))
                .andExpect(content().json("{\"id\":1,\"username\":\"Jerry\"}"))
                .andDo(print())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Data
    @AllArgsConstructor
    class UserLoginInfo {
        private String username;
        private String password;
    }
}
