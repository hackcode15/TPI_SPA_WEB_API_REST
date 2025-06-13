package com.app.JWTImplementation.repository;

import com.app.JWTImplementation.dto.projection.ProfessionalProjection;
import com.app.JWTImplementation.dto.projection.ReserveProjection;
import com.app.JWTImplementation.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {

    @Query("""
    SELECT
        u.id as id,
        u.email as email,
        u.username as username,
        u.password as password,
        u.firstName as firstName,
        u.lastName as lastName,
        p.specialty as specialty,
        p.license as license,
        p.photoUrl as photoUrl,
        u.role as role
    FROM Professional p
    JOIN User u ON p.id = u.id
    """)
    List<ProfessionalProjection> findAllProfessionals();

    @Query("""
    SELECT
        u.id as id,
        u.email as email,
        u.username as username,
        u.password as password,
        u.firstName as firstName,
        u.lastName as lastName,
        p.specialty as specialty,
        p.license as license,
        p.photoUrl as photoUrl,
        u.role as role
    FROM Professional p
    JOIN User u ON p.id = u.id
    WHERE p.id = :id
    """)
    Optional<ProfessionalProjection> findProfessionalById(@Param("id") Integer id);

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
    WHERE p.id = :id
    """)
    List<ReserveProjection> myAssignedReservations(@Param("id") Integer id);

}
