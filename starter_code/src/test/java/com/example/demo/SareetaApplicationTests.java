package com.example.demo;

import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SareetaApplicationTests {

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void forJackson1() throws JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//
//		CreateUserRequest request = new CreateUserRequest();
//		request.setUsername("Jerry");
//
//		String s = mapper.writeValueAsString(request);
//
//		System.out.println(s);
//	}

}
