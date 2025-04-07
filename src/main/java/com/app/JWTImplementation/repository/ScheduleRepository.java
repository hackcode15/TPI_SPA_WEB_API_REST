package com.app.JWTImplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {



}
