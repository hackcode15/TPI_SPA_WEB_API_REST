package com.app.JWTImplementation.repository;

import java.util.List;
import java.util.Optional;

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
        sc.startDatetime as scheduleStart,
        sc.endDatetime as scheduleEnd,
        r.status as status
    FROM Reserve r
    JOIN r.user u
    JOIN r.schedule sc
    """)
    List<ReserveProjection> findAllReservesProjection();

    @Query("""
    SELECT
        r.id as id,
        r.dateReserve as dateReserve,
        CONCAT(u.firstName, ' ', u.lastName) as userFullName,
        sc.service.name as serviceName,
        sc.startDatetime as scheduleStart,
        sc.endDatetime as scheduleEnd,
        r.status as status
    FROM Reserve r
    JOIN r.user u
    JOIN r.schedule sc
    WHERE r.id = :id
    """)
    Optional<ReserveProjection> findReserveProjectionById(@Param("id") Integer id);

}
