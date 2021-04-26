package com.tournament.restaurant.data.service.impl;

import com.tournament.restaurant.data.entities.Session;
import com.tournament.restaurant.data.repositories.SessionRepository;
import com.tournament.restaurant.data.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Autowired
    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session postSession(Session session) {

       return sessionRepository.save(session);
    }

    @Override
    public Session getSessionByToken(String token) {
        return sessionRepository.getSessionByAuthToken(token);
    }
}
