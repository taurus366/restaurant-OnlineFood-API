package com.tournament.restaurant.data.repositories;

import com.tournament.restaurant.data.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Long> {

    Food deleteFoodById(Long id);
    Food getFoodById(Long id);
}
