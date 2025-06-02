package com.app.JWTImplementation.repository;

import com.app.JWTImplementation.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

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


}
