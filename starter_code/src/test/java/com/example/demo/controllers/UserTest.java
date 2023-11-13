package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private CartRepository cartRepository;

//    private CartRepository cartRepo = mock(CartRepository.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void CreateUserHappyPath() {
        when(bEncoder.encode("password")).thenReturn("thisIsHashed");

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Jerry");
        r.setPassword("password");
        r.setConfirmPassword("password");
        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        HttpStatus statusCode = response.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);

        User u = response.getBody();
        assertEquals(0L, u != null ? u.getId() : -1);
        assertEquals("Jerry", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());
    }

}
