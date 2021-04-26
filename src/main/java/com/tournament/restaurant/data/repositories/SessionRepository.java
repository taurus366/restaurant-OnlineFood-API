package com.tournament.restaurant.data.repositories;

import com.tournament.restaurant.data.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {

    Session getSessionByAuthToken(String token);
}
