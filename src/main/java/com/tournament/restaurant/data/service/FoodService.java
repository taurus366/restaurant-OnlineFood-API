package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.Food;

import java.util.List;

public interface FoodService {

    void postFood(Food food);
    void deleteFood(Food food);
    List<Food> getAllFoods();
    Food getFoodById(long id);
}
