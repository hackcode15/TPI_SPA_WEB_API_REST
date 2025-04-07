package com.app.JWTImplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.ServiceMainCategory;

@Repository
public interface ServiceMainCategoryRepository extends JpaRepository<ServiceMainCategory, Integer> {
    
    

}
