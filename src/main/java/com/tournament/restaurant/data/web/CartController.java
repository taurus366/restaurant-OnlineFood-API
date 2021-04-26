package com.tournament.restaurant.data.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tournament.restaurant.data.entities.Food;
import com.tournament.restaurant.data.entities.ShoppingCart;
import com.tournament.restaurant.data.entities.User;
import com.tournament.restaurant.data.service.CartService;
import com.tournament.restaurant.data.service.FoodService;
import com.tournament.restaurant.data.service.SessionService;
import com.tournament.restaurant.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final Gson gson;

    @Autowired
    private final UserService userService;
    @Autowired
    private final FoodService foodService;
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final CartService cartService;


    public CartController(Gson gson, UserService userService, FoodService foodService, SessionService sessionService, CartService cartService) {
        this.gson = gson;
        this.userService = userService;
        this.foodService = foodService;
        this.sessionService = sessionService;
        this.cartService = cartService;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCart(@RequestBody String json) {
        Food food = foodService.getFoodById(gson.fromJson(json, Food.class).getId());
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setComment(gson.fromJson(json, ShoppingCart.class).getComment().length() > 0 ? gson.fromJson(json, ShoppingCart.class).getComment() : "без коментар");

        User userFromDB = sessionService.getSessionByToken(gson.fromJson(json, User.class).getSession().getAuthToken()).getUser();

        if (userFromDB != null && food != null) {

            shoppingCart.setFood(food);
            shoppingCart.setUser(userFromDB);
            shoppingCart.setFoodCount(1);


            Set<ShoppingCart> cartSet = cartService.getAllCartByUser(userFromDB);

            for (ShoppingCart cart : cartSet) {
                if (cart.getFood().getId().equals(shoppingCart.getFood().getId()) && cart.getComment().equals(shoppingCart.getComment())) {
                    cart.setFoodCount(cart.getFoodCount() + 1);
                    cartService.putCart(cartSet);
                    return new ResponseEntity<>(gson.toJson("Артикула съществува в кошницата обновихме бройката."), new HttpHeaders(), HttpStatus.OK);
                }
            }

            cartService.postCart(shoppingCart);


            return new ResponseEntity<>(gson.toJson("Успешно добавихте в кошницата."), new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(gson.toJson("Моля влезте отново , сесията ви е изтекла!"), new HttpHeaders(), HttpStatus.UNAUTHORIZED);


    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCartByUser(@RequestBody String json) {
        try {
            User userFromDB = sessionService.getSessionByToken(gson.fromJson(json, User.class).getSession().getAuthToken()).getUser();
            List<ShoppingCart> carts = userFromDB.getShoppingCarts();

            return new ResponseEntity<>(gson.toJson(carts), new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Моля влезте сесията е изтекъл."), HttpStatus.UNAUTHORIZED);
        }


    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editProductQuantity(@RequestBody String json) {
        String authToken = gson.fromJson(json, User.class).getSession().getAuthToken();
        String type = gson.fromJson(json, JsonObject.class).get("type").getAsString();
        long cartId = gson.fromJson(json, JsonObject.class).get("cartId").getAsLong();
        User user = sessionService.getSessionByToken(authToken).getUser();

       if (user != null){
           Set<ShoppingCart> cart = cartService.getAllCartByUser(user);
           String message = type.equals("plus") ? "успешно обновихте кошницата" : type.equals("minus") ? "успешно обновихте кошницата" : type.equals("delete") ? "успешно изтрихте артикула от кошницата" : "";
           if (type.equals("minus") || type.equals("plus")){
               for (ShoppingCart cart1 : cart) {
                   if (cart1.getId() == cartId && cart1.getUser() == user){
                       if (type.equals("plus")){
                           cart1.setFoodCount(cart1.getFoodCount() + 1);
                       }else {
                           cart1.setFoodCount(cart1.getFoodCount() - 1);
                       }
                       cartService.putCart(cart);

                       return new ResponseEntity<>(gson.toJson(message), new HttpHeaders(), HttpStatus.OK);
                   }

               }
               return new ResponseEntity<>(gson.toJson("неможахме да открием артикула."), new HttpHeaders(), HttpStatus.NOT_FOUND);
           }else if (type.equals("delete")){
                cart
                        .forEach(cart1 -> {
                            if (cart1.getId() == cartId && cart1.getUser() == user){
                                cartService.deleteCart(cart1);
                            }
                        });
               return new ResponseEntity<>(gson.toJson(message), new HttpHeaders(), HttpStatus.OK);
           }



       }
       return new ResponseEntity<>(gson.toJson("Моля влезте в системата отново сесията ви е изтекъл"), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

}
