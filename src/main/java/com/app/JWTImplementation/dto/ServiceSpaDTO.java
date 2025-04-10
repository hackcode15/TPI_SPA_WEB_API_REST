package com.app.JWTImplementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSpaDTO {

    // Creacion y Lectura

    private Integer id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private Boolean isActive;
    
}
