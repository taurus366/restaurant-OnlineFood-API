package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.User;

import java.util.List;

public interface UserService {

    User getUserByUsername(String username);
    User postUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
}
