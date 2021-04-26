package com.tournament.restaurant.data.repositories;

import com.tournament.restaurant.data.entities.ConfirmedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmOrderRepository extends JpaRepository<ConfirmedOrder,Long> {
}
