package com.app.JWTImplementation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.app.JWTImplementation.model.Reserve.StatusReserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveInfoDTO {
    private Integer id;
    private LocalDate dateReserve;
    private String userFullName;
    private String serviceName;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private StatusReserve status;
}
