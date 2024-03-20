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
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

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
    public void createUserHappyPath() {

        final ResponseEntity<User> response = prepareUserResponseEntity();

        // assertions
        assertNotNull(response);
        System.out.println(response.getHeaders());

        assertEquals(200, response.getStatusCodeValue());
//        HttpStatus statusCode = response.getStatusCode();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User u = response.getBody();
        assertEquals(0L, u != null ? u.getId() : -1);
        assertEquals("Jerry", u.getUsername());

        assertEquals("thisIsHashed", u.getPassword());
    }



    @Test
    public void createUserUnhappyPath() {
        // prepare params
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Jerry");
        r.setPassword("pwd");
        r.setConfirmPassword("pwd");
        final ResponseEntity<User> response = userController.createUser(r);

        // call
        assertNotNull(response);
        System.out.println(response.getHeaders());

        // assertions
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void findByIdHappyPath() {
        // prepare params
        final ResponseEntity<User> response = prepareUserResponseEntity();
        User user0 = response.getBody();

        // compare these 2: 可以得到，user0的id是new User的时候就获得的，但userById要从DB中取，此处无对象。
//        assertEquals(0L, user0 != null ? user0.getId() : -1);
//        User userById = userController.findById(0L).getBody();
//        assertEquals(user0, userById);

        // call main method
            // stub
        when(userRepository.findById(user0.getId())).thenReturn(Optional.of(user0));
        ResponseEntity<User> responseEntity = userController.findById(user0.getId());
        System.out.println(responseEntity);
        User userById = responseEntity.getBody();

        // assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(user0.getUsername(), userById.getUsername());
    }

    @Test
    public void findByUserName() {
        // prepare params
        final ResponseEntity<User> response = prepareUserResponseEntity();
        User user0 = response.getBody();

        // call main method
            // stub
        when(userRepository.findByUsername(user0.getUsername())).thenReturn(user0);
        ResponseEntity<User> responseEntity = userController.findByUserName("Jerry");
        System.out.println(responseEntity);
        User userById = responseEntity.getBody();

        // assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(user0.getUsername(), userById.getUsername());

        // unhappy path
        ResponseEntity<User> responseEntity1 = userController.findByUserName("Jered");
        System.out.println(responseEntity1);
        assertEquals(404, responseEntity1.getStatusCodeValue());
        assertNull(responseEntity1.getBody());
    }

    private ResponseEntity<User> prepareUserResponseEntity() {
        // prepare params
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Jerry");
        r.setPassword("password");
        r.setConfirmPassword("password");

        // call main method
        // stub
        when(bEncoder.encode("password")).thenReturn("thisIsHashed");
        final ResponseEntity<User> response = userController.createUser(r);
        return response;
    }
}
