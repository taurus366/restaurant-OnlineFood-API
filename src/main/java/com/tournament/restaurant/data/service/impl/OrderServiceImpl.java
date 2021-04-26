package com.tournament.restaurant.data.service.impl;

import com.tournament.restaurant.data.entities.Order;
import com.tournament.restaurant.data.entities.User;
import com.tournament.restaurant.data.repositories.OrderRepository;
import com.tournament.restaurant.data.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void postOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Set<Order> getAllOrdersByTime() {
        return orderRepository.findAllOrdersOrderByAsc();
    }

    @Override
    public void deleteAllByUser(User user) {
        orderRepository.deleteAllByUser(user);
    }

    @Override
    public Set<Order> findOrderByUser(User user) {
        return orderRepository.findAllByUser(user);
    }
}
