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

    // Creacion

    //private Integer id;
    private String name;
    private String description;
    private String category;
    private Integer durationMinutes;
    private Boolean isActive;
    private Boolean isGroupService;

}
