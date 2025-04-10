package com.app.JWTImplementation.dto.generationSchedulesDTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyScheduleDTO {
    private String day; // Fecha en formato "yyyy-MM-dd"
    private List<ServiceScheduleDTO> services;
}
