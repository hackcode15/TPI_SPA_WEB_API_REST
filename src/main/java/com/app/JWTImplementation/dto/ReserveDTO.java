package com.app.JWTImplementation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveDTO {
    
    private Integer id;
    private LocalDate reservationDate;
    private String nameUser;
    private String nameServiceSpa;
    private LocalDate dateReserve;
    private LocalTime hourReserve;

}
