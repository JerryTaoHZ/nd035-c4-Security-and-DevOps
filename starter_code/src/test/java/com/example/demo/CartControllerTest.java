package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;

    @Test
    public void addToCartHappyPath() {

        when(userRepository.findByUsername(any())).thenReturn(
                MockingData.buildMockUser(MockingData.buildMockCart(null)));
        when(itemRepository.findById(any())).thenReturn(
                Optional.of(MockingData.buildMockItem()));

        ResponseEntity<Cart> response = cartController.addToCart(new ModifyCartRequest());
        Cart cart = response.getBody();
        System.out.println(response);
        System.out.println(cart);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert cart != null;
        assertEquals(MockingData.buildMockItem(), cart.getItems().get(0));
    }

    @Test
    public void CartNullUser() {

        when(userRepository.findByUsername(any())).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addToCart(new ModifyCartRequest());
        Cart cart = response.getBody();
        System.out.println(response);
        System.out.println(cart);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(cart);

        ResponseEntity<Cart> removeResponse = cartController.removeFromCart(new ModifyCartRequest());
        System.out.println(removeResponse);
        assertEquals(HttpStatus.NOT_FOUND, removeResponse.getStatusCode());
    }

    @Test
    public void CartNullItem() {

        when(userRepository.findByUsername(any())).thenReturn(
                MockingData.buildMockUser(MockingData.buildMockCart(null)));
        when(itemRepository.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.addToCart(new ModifyCartRequest());
        Cart cart = response.getBody();
        System.out.println(response);
        System.out.println(cart);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(cart);

        ResponseEntity<Cart> response1 = cartController.removeFromCart(new ModifyCartRequest());
        Cart cart1 = response1.getBody();
        System.out.println(response1);
        System.out.println(cart1);

        assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
        assertNull(cart1);
    }

    @Test
    public void removeFromCartHappyPath() {

        when(userRepository.findByUsername(any())).thenReturn(
                MockingData.buildMockUser(MockingData.buildMockCart(null)));
        when(itemRepository.findById(any())).thenReturn(
                Optional.of(MockingData.buildMockItem()));

        ResponseEntity<Cart> response0 = cartController.addToCart(new ModifyCartRequest());
        Cart cart0 = response0.getBody();
//        System.out.println(response0);
//        System.out.println(cart);

//        assertEquals(HttpStatus.OK, response0.getStatusCode());
//        assert cart != null;
//        assertEquals(MockingData.buildMockItem(), cart.getItems().get(0));

        ModifyCartRequest cartReq = new ModifyCartRequest("Jerry", 0L, 1);

        ResponseEntity<Cart> response = cartController.removeFromCart(cartReq);
        Cart cart = response.getBody();
        System.out.println(response);
        System.out.println(cart.getItems());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, cart.getItems().size());
    }
}
