package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getItems() {

        Item item = MockingData.buildMockItem();
        when(itemRepository.findAll()).thenReturn(Lists.newArrayList(item));

        // call targeted method
        ResponseEntity<List<Item>> response = itemController.getItems();
        System.out.println(response);

        // assertions:

        // 查不到数据库，是null
//        assertNull(response.getBody().get(0));
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // compare with result from repository
        assertEquals(itemRepository.findAll(), response.getBody());
    }

    @Test
    public void getItemsById() {
        Item item0 = MockingData.buildMockItem();
        when(itemRepository.findById(any())).thenReturn(Optional.of(item0));

        ResponseEntity<Item> response = itemController.getItemById(0L);
        System.out.println(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item0, response.getBody());
    }

//    @Test
//    public void getItemsById0() {
//        // think: how to connect h2?
//
//        System.out.println(itemController.getItemById(1l));
//
////        Item itemId = Objects.requireNonNull(itemController.getItemById(1l).getBody());
////
////        Item itemRef = itemRepository.getOne(1L);
////
////        // testing overwritten hashcode method
////        Assert.assertTrue(itemId.hashCode() == itemRef.hashCode());
////
////        // testing overwritten equals method
////        Assert.assertTrue(itemId.equals(itemRef));
//
//    }

    @Test
    public void getItemsByName() {
        Item item0 = MockingData.buildMockItem();

        when(itemRepository.findByName(any())).thenReturn(
                Collections.singletonList(item0)
        );

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Round Widget");
        System.out.println(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item0, response.getBody().get(0));
    }

}
