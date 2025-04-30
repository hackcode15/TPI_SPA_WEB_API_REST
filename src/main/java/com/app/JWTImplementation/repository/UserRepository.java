package com.app.JWTImplementation.repository;

import java.util.List;
import java.util.Optional;

import com.app.JWTImplementation.dto.projection.UserHistoryReservationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findUserByUsername(String username);

    // Historial de reserva de un cliente particular
    @Query("""
    SELECT
        u.id as userId,
        r.dateReserve as reserveDateTime,
        CONCAT(u.firstName, ', ', u.lastName) as userFullName,
        s.name as serviceName,
        sc.startDatetime as serviceStartDatetime,
        sc.endDatetime as serviceEndDatetime,
        r.status as reserveStatusName
    FROM User u
    JOIN u.reserves r
    JOIN r.schedule sc
    JOIN sc.service s
    WHERE u.id = :userId
    ORDER BY r.dateReserve ASC
    """)
    List<UserHistoryReservationProjection> findAllHistoryReservationById(@Param("userId") Integer userId);

}
