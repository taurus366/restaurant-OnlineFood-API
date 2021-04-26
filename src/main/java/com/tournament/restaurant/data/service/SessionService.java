package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.Session;

public interface SessionService {
    Session postSession(Session session);
    Session getSessionByToken(String token);
   // User postUser(User user);
}
