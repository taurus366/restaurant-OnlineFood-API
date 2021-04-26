package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.Order;
import com.tournament.restaurant.data.entities.User;

import java.util.Set;

public interface OrderService {
    void postOrder(Order order);
    Set<Order> getAllOrdersByTime();
    void deleteAllByUser(User user);
    Set<Order> findOrderByUser(User user);
}
