package com.app.JWTImplementation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

//import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleDTO {
    
    private Integer id;
    //private LocalDateTime startDatetime;
    //private LocalDateTime endDateTime;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Integer maxCapacity;
    private Integer currentCapacity;
    private Boolean isActive;

}
