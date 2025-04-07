package com.app.JWTImplementation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.ServiceSpa;

@Repository
public interface ServiceSpaRepository extends JpaRepository<ServiceSpa, Integer> {

    List<ServiceSpa> findByName(String name);
    
}
