package com.app.JWTImplementation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.Schedule;
import java.time.LocalDate;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    
    @Query(
    value = """
            SELECT 
            *
            FROM tbl_schedule
            where date = :date
            """,
    nativeQuery = true
    )
    List<Schedule> findByDate(@Param("date") LocalDate date);

}
