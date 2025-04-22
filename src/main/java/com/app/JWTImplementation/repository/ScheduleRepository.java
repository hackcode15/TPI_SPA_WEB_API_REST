package com.app.JWTImplementation.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.app.JWTImplementation.dto.ScheduleInfoDTO;
import com.app.JWTImplementation.dto.projection.ScheduleProjection;
import com.app.JWTImplementation.model.ServiceSpa;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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

    Optional<Schedule> findByServiceNameAndStartDatetime(String serviceName, LocalDateTime startDatetime);

    @Query("SELECT s FROM Schedule s WHERE s.service = :service AND DATE(s.startDatetime) = :date")
    List<Schedule> findByServiceAndDate(@Param("service") ServiceSpa service, @Param("date") LocalDate date);

    @Query("SELECT s FROM Schedule s WHERE s.service.id = :serviceId " +
            "AND s.startDatetime BETWEEN :startDate AND :endDate " +
            "AND s.isActive = true")
    List<Schedule> findActiveByServiceAndDateRange(
            @Param("serviceId") Integer serviceId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Schedule> findWithLockingById(Integer id);

    // En ScheduleRepository
    @Query("SELECT s FROM Schedule s WHERE s.service.id = :serviceId AND DATE(s.startDatetime) = :date")
    List<Schedule> findByServiceIdAndDate(@Param("serviceId") Integer serviceId, @Param("date") LocalDate date);

    //Optional<Schedule> findByServiceAndStartDatetime(ServiceSpa service, LocalDateTime startDatetime);

    List<Schedule> findByServiceAndStartDatetime(ServiceSpa service, LocalDateTime startDatetime);

}
