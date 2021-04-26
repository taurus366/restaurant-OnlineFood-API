package com.tournament.restaurant.data.web;

import com.tournament.restaurant.data.entities.Role;
import com.tournament.restaurant.data.entities.Session;
import com.tournament.restaurant.data.entities.User;
import com.tournament.restaurant.data.service.SessionService;
import com.tournament.restaurant.data.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Gson gson;

    @Autowired
    private final UserService userService;
    @Autowired
    private final SessionService sessionService;

    public UserController(Gson gson, UserService userService, SessionService sessionService) {
        this.gson = gson;
        this.userService = userService;

        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logUser(@RequestBody String userJson) {
        User user = gson.fromJson(userJson, User.class);

        if (checkIfUserExist(user.getUsername(), user.getPassword())) {


            User respond = userService.getUserByUsername(user.getUsername());

            return new ResponseEntity<>(gson.toJson(respond), new HttpHeaders(), HttpStatus.CREATED);

//            return ResponseEntity.created(ServletUriComponentsBuilder
//                    .fromCurrentRequest()
//                    .pathSegment("{id}")
//                    .buildAndExpand(respond.getId()
//                            .toString())
//                    .toUri())
//                    .body(gson
//                            .toJson(respond));
        }
        return new ResponseEntity<>(gson.toJson("Грешно Потребителско име или парола!"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> addUser(@RequestBody String json) {
        User user = gson.fromJson(json, User.class);
        if (checkIfUserExist(user.getUsername(), null)) {
            return new ResponseEntity<>(gson.toJson("Потребителското име вече съществува!"), HttpStatus.UNAUTHORIZED);
        }
        user.setRole(Role.CLIENT);
        Session session = new Session();
        session.setAuthToken(generateAuthToken());
        session.setUser(user);
        session.setCreatedTime(new Timestamp(System.currentTimeMillis()));

        user.setSession(session);
        User created = userService.postUser(user);

        return new ResponseEntity<>(gson.toJson(created), new HttpHeaders(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/change/information", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeUserInformation(@RequestBody String json) {

        User user = gson.fromJson(json, User.class);
        User dbUser = userService.getUserById(user.getId());

        if (dbUser != null && dbUser.getSession().getAuthToken().equals(user.getSession().getAuthToken())) {
            if (user.getAddress() != null) {
                dbUser.setAddress(user.getAddress());
            }
            if (user.getFullName() != null) {
                dbUser.setFullName(user.getFullName());
            }
            if (user.getPhoneNumber() != null) {
                dbUser.setPhoneNumber(user.getPhoneNumber());
            }
            return new ResponseEntity<>(gson.toJson(userService.postUser(dbUser)), new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(gson.toJson("Моля влезте в системата!"), HttpStatus.UNAUTHORIZED);


    }


    private Boolean checkIfUserExist(String username, String password) {
        User user = userService.getUserByUsername(username);
        if (password == null) {
            return user != null;
        }
        if (user == null) {
            return false;
        }
        return user.getUsername().equals(username) && user.getPassword().equals(password);

    }

    private String generateAuthToken() {
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();

        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);


    }
}
