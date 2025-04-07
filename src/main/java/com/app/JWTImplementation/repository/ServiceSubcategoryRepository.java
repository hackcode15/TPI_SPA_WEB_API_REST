package com.app.JWTImplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.ServiceSubcategory;

@Repository
public interface ServiceSubcategoryRepository extends JpaRepository<ServiceSubcategory, Integer> {
    
    

}
