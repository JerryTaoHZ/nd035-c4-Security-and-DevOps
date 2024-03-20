package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class MockingData {

    public static Item buildMockItem() {
        return new Item(0L, "Round Widget", BigDecimal.valueOf(2.99), "A widget that is round");
    }

    public static User buildMockUser(Cart cart) {
        return new User(1L, "Jerry", "password", cart);
    }

    public static Cart buildMockCart(User user) {
        return new Cart(2L, Lists.newArrayList(buildMockItem()), user, BigDecimal.valueOf(2.99));
    }

    public static UserOrder buildMockOrder(User user) {
        return new UserOrder(3L, Lists.newArrayList(buildMockItem()),
                buildMockUser(null), BigDecimal.valueOf(2.99));
    }
}
