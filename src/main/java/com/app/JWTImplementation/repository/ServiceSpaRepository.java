package com.app.JWTImplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.ServiceSpa;

@Repository
public interface ServiceSpaRepository extends JpaRepository<ServiceSpa, Integer> {
    
}
