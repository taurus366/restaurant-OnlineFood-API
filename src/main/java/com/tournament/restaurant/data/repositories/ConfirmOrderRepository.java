package com.tournament.restaurant.data.repositories;

import com.tournament.restaurant.data.entities.ConfirmedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmOrderRepository extends JpaRepository<ConfirmedOrder,Long> {
    List<ConfirmedOrder> findAllByConfirmDateContaining(String confirmDate);
}
