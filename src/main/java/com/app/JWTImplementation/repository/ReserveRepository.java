package com.app.JWTImplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    
}
