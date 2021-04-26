package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.ShoppingCart;
import com.tournament.restaurant.data.entities.User;

import java.util.Set;

public interface CartService {

    void postCart(ShoppingCart cart);
    Set<ShoppingCart> getAllCartByUser(User user);
    void putCart(Set<ShoppingCart> cart);
    void deleteCart(ShoppingCart cart);
    void deleteAllCartByUser(User user);
}
