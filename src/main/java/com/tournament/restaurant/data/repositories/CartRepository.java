package com.tournament.restaurant.data.repositories;


import com.tournament.restaurant.data.entities.ShoppingCart;
import com.tournament.restaurant.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartRepository extends JpaRepository<ShoppingCart,Long> {

    Set<ShoppingCart> findAllByUser(User user);
    void deleteAllByUser(User user);
}
