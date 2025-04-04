package com.app.JWTImplementation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveRequestDTO {
    
    @NotNull(message = "User ID is required")
    @Positive(message = "The user ID must be a positive number")
    private Integer userID;
    
    @NotNull(message = "Service ID is required")
    @Positive(message = "The service ID must be a positive number")
    private Integer serviceSpaID;
    
    @NotNull(message = "The schedule ID is required")
    @Positive(message = "The schedule ID must be a positive number")
    private Integer scheduleID;
}
