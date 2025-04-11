package com.app.JWTImplementation.repository;

import java.util.List;

import com.app.JWTImplementation.dto.projection.ScheduleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.Schedule;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("""
    SELECT
        sc.id as id,
        sc.startDatetime as startDatetime,
        sc.endDatetime as endDatetime,
        sc.maxCapacity as maxCapacity,
        sc.currentCapacity as currentCapacity,
        sc.isActive as isActive,
        s.name as serviceName
    FROM Schedule sc
    JOIN sc.service s
    WHERE CAST(sc.startDatetime AS localdate) = :date AND CAST(sc.endDatetime AS localdate) = :date
    """)
    List<ScheduleProjection> findByDate(@Param("date") LocalDate date);

    @Query("""
    SELECT
        sc.id as id,
        sc.startDatetime as startDatetime,
        sc.endDatetime as endDatetime,
        sc.maxCapacity as maxCapacity,
        sc.currentCapacity as currentCapacity,
        sc.isActive as isActive,
        s.name as serviceName
    FROM Schedule sc
    JOIN sc.service s
    WHERE (CAST(sc.startDatetime AS localdate) = :date AND CAST(sc.endDatetime AS localdate) = :date) AND s.id = :id
    """)
    List<ScheduleProjection> findAllSchedulesOfServiceByDate(@Param("date") LocalDate date, @Param("id") Integer id);

    @Query("""
    SELECT
        sc.id as id,
        sc.startDatetime as startDatetime,
        sc.endDatetime as endDatetime,
        sc.maxCapacity as maxCapacity,
        sc.currentCapacity as currentCapacity,
        sc.isActive as isActive,
        s.name as serviceName
    FROM Schedule sc
    JOIN sc.service s
    """)
    List<ScheduleProjection> findAllSchedulesWithEntities();

    @Query("""
    SELECT
        sc.id as id,
        sc.startDatetime as startDatetime,
        sc.endDatetime as endDatetime,
        sc.maxCapacity as maxCapacity,
        sc.currentCapacity as currentCapacity,
        sc.isActive as isActive,
        s.name as serviceName
    FROM Schedule sc
    JOIN sc.service s
    WHERE sc.id = :id
    """)
    Optional<ScheduleProjection> findScheduleByIdWithEntity(@Param("id") Integer id);

}
