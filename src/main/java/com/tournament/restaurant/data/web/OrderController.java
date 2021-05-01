package com.tournament.restaurant.data.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tournament.restaurant.data.entities.*;
import com.tournament.restaurant.data.service.*;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private final Gson gson;
    @Autowired
    private final SessionService sessionService;

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final CartService cartService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ConfirmOrderService confirmOrderService;

    public OrderController(Gson gson, SessionService sessionService, OrderService orderService, CartService cartService, UserService userService, ConfirmOrderService confirmOrderService) {
        this.gson = gson;
        this.sessionService = sessionService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
        this.confirmOrderService = confirmOrderService;
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postOrder(@RequestBody String json) {
        String authToken = gson.fromJson(json, JsonObject.class).get("authToken").getAsString();

        User user = sessionService.getSessionByToken(authToken).getUser();
        if (user != null) {
            List<ShoppingCart> carts = user.getShoppingCarts();
            if (carts.size() > 0) {
                for (ShoppingCart cart : carts) {
                    Timestamp ts = new Timestamp(System.currentTimeMillis());

                    Order order = new Order();
                    order.setComment(cart.getComment());
                    order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts));
                    order.setFoodCount(cart.getFoodCount());
                    order.setFood(cart.getFood());
                    order.setUser(cart.getUser());
                    order.setPrice(cart.getFood().getPrice());
                    order.setAddress(cart.getUser().getAddress());
                    order.setFullName(cart.getUser().getFullName());
                    order.setPhoneNumber(cart.getUser().getPhoneNumber());
                    orderService.postOrder(order);
                }
                cartService.deleteAllCartByUser(user);
                return new ResponseEntity<>(gson.toJson("успешно поръчахте, Благодарим ви !"), new HttpHeaders(), HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(gson.toJson("кошницата ви е празна!"), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson("Моля влезте отново в системата / сесията ви е приключила."), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrders(@RequestBody String json) {
        User user = sessionService.getSessionByToken(gson.fromJson(json, JsonObject.class).get("authToken").getAsString()).getUser();

        if (user != null) {
            if (user.getRole().equals(Role.ADMIN)) {
                Set<Order> list = orderService.getAllOrdersByTime();
                HashMap<String, Set<Order>> lists = new LinkedHashMap<>();

                for (Order order : list) {
                    String fullName = order.getUser().getUsername();

                    if (!lists.containsKey(fullName)) {
                        lists.put(fullName, new LinkedHashSet<>());
                        lists.get(fullName).add(order);
                    } else if (lists.containsKey(fullName)) {
                        lists.get(fullName).add(order);
                    }
                }

                return new ResponseEntity<>(gson.toJson(lists), new HttpHeaders(), HttpStatus.OK);
            }
            return new ResponseEntity<>(gson.toJson("Неможахме да ви верифицираме като АДМИН."), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(gson.toJson("Моля влезте отново , сесията ви е изтекъл!"), new HttpHeaders(), HttpStatus.UNAUTHORIZED);

    }



    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> confirmOrDeleteOrders(@RequestBody String json) {
        String username = gson.fromJson(json, JsonObject.class).get("username").getAsString();
        String type = gson.fromJson(json, JsonObject.class).get("type").getAsString();
        String authToken = gson.fromJson(json, JsonObject.class).get("authToken").getAsString();

        User user = sessionService.getSessionByToken(authToken).getUser();
        User user1 = userService.getUserByUsername(username);
        if (user != null && user.getRole().equals(Role.ADMIN) && username.length() != 0 && user1 != null) {

            Timestamp ts = new Timestamp(System.currentTimeMillis());


            Set<Order> userOrder = orderService.findOrderByUser(user1);
            Map<String, Map<String, Double>> confOrder = new LinkedHashMap<>();
            if (userOrder.size() > 0) {
                switch (type) {
                    case "confirm-btn":
                        userOrder
                                .forEach(us -> {
                                    String foodName = us.getFood().getName();
                                    double price = us.getFood().getPrice();
                                    double foodCount = us.getFoodCount();

                                    if (!confOrder.containsKey(foodName)) {
                                        confOrder.put(foodName, new LinkedHashMap<>());
                                        confOrder.get(foodName).put("price", price * foodCount);
                                        confOrder.get(foodName).put("foodCount", foodCount);
                                    } else {
                                        confOrder.get(foodName).put("price", confOrder.get(foodName).get("price") + (price * foodCount));
                                        confOrder.get(foodName).put("foodCount", confOrder.get(foodName).get("foodCount") + foodCount);
                                    }
                                });
                        confOrder
                                .forEach((k, v) -> {

                                    ConfirmedOrder confirmedOrder = new ConfirmedOrder();
                                    confirmedOrder.setConfirmDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts));
                                    confirmedOrder.setFoodName(k);
                                    confirmedOrder.setPrice(v.get("price"));
                                    confirmedOrder.setFoodCount(Integer.parseInt(String.valueOf(v.get("foodCount").intValue())));
                                    confirmOrderService.postConfirmOrder(confirmedOrder);

                                });
                        orderService.deleteAllByUser(user1);

                        return new ResponseEntity<>(gson.toJson("Успешно потвърдихте поръчката!"), new HttpHeaders(),HttpStatus.OK);

                    case "delete-btn":
                        orderService.deleteAllByUser(user1);
                        return new ResponseEntity<>(gson.toJson("Успешно изтрихте поръчката!"), new HttpHeaders(),HttpStatus.OK);
                }
            }
            else {
                return new ResponseEntity<>(gson.toJson("Такава поръчка не съществува в системата!"), new HttpHeaders(),HttpStatus.NOT_FOUND);
            }


        }


        return new ResponseEntity<>(gson.toJson("Моля влезте отново в системата/Рефрешнете страницата!"),new HttpHeaders(),HttpStatus.UNAUTHORIZED);
    }

}
