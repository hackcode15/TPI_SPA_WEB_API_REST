package com.app.JWTImplementation.dto;

import com.app.JWTImplementation.model.ServiceSpa.StatusEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceSpaDTO {
    
    private Integer id;

    @NotBlank // El campo name no debe estar vacio
    @Size(min = 4, max = 45, message = "Name must be between 4 and 45 characters") // Longitud del username
    private String name;

    @Size(min = 4, max = 100, message = "Description must be between 4 and 45 characters") // Longitud del username
    private String description;

    @NotBlank
    private StatusEnum status_name;

}
