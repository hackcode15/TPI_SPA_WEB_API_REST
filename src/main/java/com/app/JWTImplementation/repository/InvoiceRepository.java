package com.app.JWTImplementation.repository;

import com.app.JWTImplementation.dto.projection.InvoiceProjection;
import com.app.JWTImplementation.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(
    """
    SELECT
        SUM(i.total) as total
    FROM Invoice i
    JOIN i.reserve r
    WHERE DATE(r.dateReserve) BETWEEN :startDate AND :endDate
    """)
    BigDecimal getTotalIncome(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
    """
    SELECT
        SUM(i.total) as total
    FROM Invoice i
    JOIN i.reserve r
    WHERE DATE(r.dateReserve) BETWEEN :startDate AND :endDate
    """)
    BigDecimal getHistoryIncomeByProfessional(@Param("idProfessional") Integer id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
    """
    SELECT
        i.id as id,
        i.invoiceNumber as invoiceNumber,
        i.issueDate as issueDate,
        i.paymentMethod as paymentMethod,
        s.name as serviceName,
        i.subtotal as subtotal,
        i.taxAmount as taxAmount,
        i.total as total,
        i.customerName as customerName,
        i.customerEmail as customerEmail,
        i.customerIdentification as customerIdentification,
        r.id as reserveId,
        i.isPaid as isPaid
    FROM Invoice i
    JOIN i.reserve r
    JOIN r.schedule sc
    JOIN sc.service s
    WHERE r.id = :idReserve
    """)
    Optional<InvoiceProjection> findByReserveId(@Param("idReserve") Integer idReserve);

}
