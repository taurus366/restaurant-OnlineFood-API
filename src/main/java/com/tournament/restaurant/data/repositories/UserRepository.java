package com.tournament.restaurant.data.repositories;

import com.tournament.restaurant.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByUsername(String username);
    User getUserById(Long id);
}
