package com.app.JWTImplementation.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.dto.projection.ReserveProjection;
import com.app.JWTImplementation.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

    @Query("""
    SELECT
        r.id as id,
        r.dateReserve as dateReserve,
        CONCAT(u.firstName, ' ', u.lastName) as userFullName,
        sc.service.name as serviceName,
        sc.service.price as servicePrice,
        sc.startDatetime as scheduleStart,
        sc.endDatetime as scheduleEnd,
        CONCAT(p.firstName, ' ', p.lastName) as professionalName,
        r.status as status
    FROM Reserve r
    JOIN r.user u
    JOIN r.schedule sc
    JOIN r.professional p
    """)
    List<ReserveProjection> findAllReservesProjection();

    @Query("""
    SELECT
        r.id as id,
        r.dateReserve as dateReserve,
        CONCAT(u.firstName, ' ', u.lastName) as userFullName,
        sc.service.name as serviceName,
        sc.service.price as servicePrice,
        sc.startDatetime as scheduleStart,
        sc.endDatetime as scheduleEnd,
        CONCAT(p.firstName, ' ', p.lastName) as professionalName,
        r.status as status
    FROM Reserve r
    JOIN r.user u
    JOIN r.schedule sc
    JOIN r.professional p
    WHERE r.id = :id
    """)
    Optional<ReserveProjection> findReserveProjectionById(@Param("id") Integer id);

    @Query("SELECT COUNT(r) > 0 FROM Reserve r " +
            "WHERE r.user.id = :userId " +
            "AND r.schedule.id = :scheduleId")
    boolean existsByUserAndSchedule(
            @Param("userId") Integer userId,
            @Param("scheduleId") Integer scheduleId);

    @Query("SELECT r FROM Reserve r WHERE r.user.id = :userId AND r.schedule.id = :scheduleId")
    List<Reserve> findByUserIdAndScheduleId(@Param("userId") Integer userId, @Param("scheduleId") Integer scheduleId);

}
