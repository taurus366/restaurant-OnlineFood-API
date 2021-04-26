package com.tournament.restaurant.data.service.impl;


import com.tournament.restaurant.data.entities.ShoppingCart;
import com.tournament.restaurant.data.entities.User;

import com.tournament.restaurant.data.repositories.CartRepository;
import com.tournament.restaurant.data.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public void postCart(ShoppingCart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Set<ShoppingCart> getAllCartByUser(User user) {
        return cartRepository.findAllByUser(user);
    }

    @Override
    public void putCart(Set<ShoppingCart> cart) {
         cartRepository.saveAll(cart);
    }

    @Override
    public void deleteCart(ShoppingCart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public void deleteAllCartByUser(User user) {
        cartRepository.deleteAllByUser(user);
    }


//    @Override
//    public ShoppingCart getOrderByUser(User user) {
//        Set<ShoppingCart> carts = cartRepository.findAllByUser(user);
//        return (ShoppingCart) carts;
//    }
}
