package com.app.JWTImplementation.dto.generationSchedulesDTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleConfigDTO {
    private String serviceName;
    private List<TimeSlotDTO> slots;
}
