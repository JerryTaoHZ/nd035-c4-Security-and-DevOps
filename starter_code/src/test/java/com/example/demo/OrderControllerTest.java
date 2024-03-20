package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void SubmitHappyPath() {
        when(userRepository.findByUsername(any())).thenReturn(
                MockingData.buildMockUser(MockingData.buildMockCart(null)));

        ResponseEntity<UserOrder> response = orderController.submit("user");
        UserOrder order = response.getBody();
        System.out.println(response);
        System.out.println(order.getItems());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MockingData.buildMockCart(null).getItems(), order.getItems());
    }

    @Test
    public void NullUser() {
        when(userRepository.findByUsername(any())).thenReturn(null);

        ResponseEntity<UserOrder> response0 = orderController.submit("user");
        System.out.println(response0);
        assertEquals(HttpStatus.NOT_FOUND, response0.getStatusCode());
        assertNull(response0.getBody());

        ResponseEntity<List<UserOrder>> response1 = orderController.getOrdersForUser("user");
        System.out.println(response1);
        assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
        assertNull(response1.getBody());
    }

    @Test
    public void getOrdersForUserHappyPath() {
        when(userRepository.findByUsername(any())).thenReturn(
                MockingData.buildMockUser(MockingData.buildMockCart(null)));
        when(orderRepository.findByUser(any())).thenReturn(
                Lists.newArrayList(MockingData.buildMockOrder(
                        MockingData.buildMockUser(null))));

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("user");
        List<UserOrder> list = response.getBody();
        System.out.println(response);
        System.out.println(list);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                MockingData.buildMockOrder(MockingData.buildMockUser(null)).getItems(),
                list.get(0).getItems());
    }
}
