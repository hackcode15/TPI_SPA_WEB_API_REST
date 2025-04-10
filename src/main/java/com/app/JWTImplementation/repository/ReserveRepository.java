package com.app.JWTImplementation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.dto.projection.ReserveProjection;
import com.app.JWTImplementation.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

    /**
     * Consulta optimizada usando proyección DTO
     * 
     * Realiza un JOIN FETCH implícito al especificar los campos necesarios
     * de las entidades relacionadas en una sola consulta SQL eficiente.
     * 
     * La consulta genera un SQL del tipo:
     * SELECT r.id, r.date_reserve, CONCAT(u.first_name,...), 
     *        s.name, sc.start_datetime, sc.end_datetime, r.status
     * FROM reserves r
     * JOIN users u ON r.user_id = u.id
     * JOIN services s ON r.service_id = s.id
     * JOIN schedules sc ON r.schedule_id = sc.id
     */
    @Query("""
        SELECT
            r.id as id,
            r.dateReserve as dateReserve,
            CONCAT(u.firstName, ', ', u.lastName) as userFullName,
            s.name as serviceName,
            sc.startDatetime as scheduleStart,
            sc.endDatetime as scheduleEnd,
            r.status as status
        FROM Reserve r
        JOIN r.user u
        JOIN r.serviceSpa s
        JOIN r.schedule sc
    """)
    List<ReserveProjection> findAllReservesProjection();

}
