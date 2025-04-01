package com.app.JWTImplementation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findUserByUsername(String username);

}
