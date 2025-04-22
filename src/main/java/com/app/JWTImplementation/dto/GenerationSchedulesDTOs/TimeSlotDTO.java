package com.app.JWTImplementation.dto.generationSchedulesDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private int availableSpots;
}
