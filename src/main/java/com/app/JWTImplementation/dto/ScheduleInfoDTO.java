package com.app.JWTImplementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleInfoDTO {

    private Integer id;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Integer maxCapacity;
    private Integer currentCapacity;
    private Boolean isActive;
    private String service;
    private Boolean available;

    public Boolean getAvailable() {
        return isActive && (currentCapacity < maxCapacity);
    }

}
