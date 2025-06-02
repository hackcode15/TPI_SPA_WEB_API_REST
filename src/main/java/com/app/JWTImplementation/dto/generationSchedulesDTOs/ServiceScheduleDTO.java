package com.app.JWTImplementation.dto.generationSchedulesDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceScheduleDTO {
    private String category;
    private String serviceName;
    private Integer duration;
    private List<TimeSlotAvailabilityDTO> slots;
}
