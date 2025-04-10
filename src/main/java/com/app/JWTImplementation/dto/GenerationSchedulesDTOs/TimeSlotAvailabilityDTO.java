package com.app.JWTImplementation.dto.generationSchedulesDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotAvailabilityDTO {
    private String startTime;
    private boolean available;
}