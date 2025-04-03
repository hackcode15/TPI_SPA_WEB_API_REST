package com.app.JWTImplementation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleDTO {
    
    private Integer id;
    private LocalDate date;
    private LocalTime hour;
    private boolean available;

}
