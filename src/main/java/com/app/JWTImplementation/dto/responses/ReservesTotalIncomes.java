package com.app.JWTImplementation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservesTotalIncomes {

    // datos de la reserva
    private Integer idReserve;
    private LocalDate dateReserve;
    private LocalTime timeReserve;
    private BigDecimal priceReserve;
    private String statusReserve;

    // datos del cliente
    private String nameCustomer;

    // datos del horario
    private LocalDate dateTurn;
    private LocalTime timeTurn;

    // datos del servicio
    private String nameService;

}
