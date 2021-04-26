package com.tournament.restaurant.data.service.impl;

import com.tournament.restaurant.data.entities.Food;
import com.tournament.restaurant.data.repositories.FoodRepository;
import com.tournament.restaurant.data.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public void postFood(Food food) {
        foodRepository.save(food);
    }

    @Override
    public void deleteFood(Food food) {
         foodRepository.delete(food);
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food getFoodById(long id) {
        return foodRepository.getFoodById(id);
    }
}
