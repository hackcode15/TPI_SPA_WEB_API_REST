package com.app.JWTImplementation.dto.GenerationSchedulesDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDTO {
    private String startTime;
    private String endTime;
}
