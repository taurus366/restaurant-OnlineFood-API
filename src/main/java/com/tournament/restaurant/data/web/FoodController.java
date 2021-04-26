package com.tournament.restaurant.data.web;

import com.google.gson.Gson;
import com.tournament.restaurant.data.entities.Food;
import com.tournament.restaurant.data.entities.Role;
import com.tournament.restaurant.data.entities.User;
import com.tournament.restaurant.data.service.FoodService;
import com.tournament.restaurant.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/foods")
public class FoodController {
    @Autowired
    private final Gson gson;

    @Autowired
    private final FoodService foodService;
    @Autowired
    private final UserService userService;

    public FoodController(Gson gson, FoodService foodService, UserService userService) {
        this.gson = gson;
        this.foodService = foodService;

        this.userService = userService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postFood(@RequestBody String json){

       Food food = gson.fromJson(json, Food.class);
        food.setId(null);
        User user = gson.fromJson(json, User.class);

        User userFromDB = userService.getUserById(user.getId());
        if (userFromDB != null && userFromDB.getRole().equals(Role.ADMIN)){
            foodService.postFood(food);
            return new ResponseEntity<>(gson.toJson("created"), new HttpHeaders(), HttpStatus.CREATED);
        }


       return new ResponseEntity<>(gson.toJson("НЕ сте познат като АДМИН от системата"), new HttpHeaders(),HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllFoods() {
        List<Food> allFoods = foodService.getAllFoods();
        if (allFoods.size() > 0) {
            return new ResponseEntity<>(gson.toJson(allFoods), new HttpHeaders(), HttpStatus.OK);

        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFoodByID(@PathVariable String id) {

        Food food = foodService.getFoodById(Long.parseLong(id));
        if (food != null) {
            return new ResponseEntity<>(gson.toJson(food), new HttpHeaders(), HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFoodById(@PathVariable String id, @RequestBody String json) {

        Food food = foodService.getFoodById(Long.parseLong(id));
        User user = gson.fromJson(json, User.class);
        User userFromDb = userService.getUserById(user.getId());
        if (userFromDb != null && userFromDb.getRole().equals(Role.ADMIN)) {
            foodService.deleteFood(food);
            return ResponseEntity.ok(gson.toJson("Успешно изтрихте " + food.getName() + " от каталога"));
        }
        return new ResponseEntity<>(gson.toJson("Не ви е разрешен да изтривате данни от каталога като клиент!"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/add2", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editFood(@RequestBody String json){
        Food food = gson.fromJson(json, Food.class);

        Food foodFromDB = foodService.getFoodById(food.getId());
        if (foodFromDB != null){
            foodService.postFood(food);
            return ResponseEntity.ok(gson.toJson("Успешно променихте информацията."));
        }



        return new ResponseEntity<>(gson.toJson("не съществува!"), new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

}
