package com.app.JWTImplementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCategoryDTO {
    
    // Lectura

    private Integer id;
    private String name;
    private String description;
    private Boolean isGroupService;

}
