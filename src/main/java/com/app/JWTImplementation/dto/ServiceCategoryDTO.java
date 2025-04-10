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

    // Modificar para la nueva version
    private Integer id;
    private String name;

}
