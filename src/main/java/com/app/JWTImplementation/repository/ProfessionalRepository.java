package com.app.JWTImplementation.repository;

import com.app.JWTImplementation.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {


}
