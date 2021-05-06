package com.tournament.restaurant.data.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tournament.restaurant.data.entities.ConfirmedOrder;
import com.tournament.restaurant.data.entities.Role;
import com.tournament.restaurant.data.entities.Session;
import com.tournament.restaurant.data.entities.User;
import com.tournament.restaurant.data.service.ConfirmOrderService;
import com.tournament.restaurant.data.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/confirm")
public class ConfirmOrderController {

    @Autowired
    private final Gson gson;
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final ConfirmOrderService confirmOrderService;

    public ConfirmOrderController(Gson gson, SessionService sessionService, ConfirmOrderService confirmOrderService) {
        this.gson = gson;
        this.sessionService = sessionService;
        this.confirmOrderService = confirmOrderService;
    }


    @RequestMapping(value = "/confirmed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getConfirmedOrdersByDate(@RequestBody String json){
        String authToken = gson.fromJson(json, JsonObject.class).get("authToken").getAsString();
        String date = gson.fromJson(json, JsonObject.class).get("date").getAsString();

        User user = sessionService.getSessionByToken(authToken).getUser();
        System.out.println(date);
        if (user != null && user.getRole().equals(Role.ADMIN)){
            List<ConfirmedOrder> confirmedOrderList = confirmOrderService.getConfirmOrderByDate(date);
            System.out.println(confirmedOrderList.size());
           if (confirmedOrderList.size() > 0){
               return new ResponseEntity<>(gson.toJson(confirmedOrderList), new HttpHeaders(),HttpStatus.OK);
           }else {
               return new ResponseEntity<>(gson.toJson("Няма поръчка за тази дата!"), new HttpHeaders(), HttpStatus.NOT_FOUND);
           }

        }
        return new ResponseEntity<>(gson.toJson("Моля влезте в системата като админ!"),new HttpHeaders(),HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/dates",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllConfirmedOrdersDate(@RequestBody String json){
        String authToken = gson.fromJson(json, JsonObject.class).get("authToken").getAsString();
        Session session = sessionService.getSessionByToken(authToken);
        if (session != null && session.getUser().getRole().equals(Role.ADMIN)){
            List<String> list = new LinkedList<>();
            confirmOrderService.getAllConfirms().forEach(line -> {
                System.out.println(line.getConfirmDate());
                if (!list.contains(line.getConfirmDate().substring(0, 10))){
                    list.add(line.getConfirmDate().substring(0, 10));
                }
            });

            return new ResponseEntity<>(gson.toJson(list),new HttpHeaders(),HttpStatus.OK);
        }
        return new ResponseEntity<>(gson.toJson("Моля влезте като администратор"),new HttpHeaders(),HttpStatus.UNAUTHORIZED);

    }
}
