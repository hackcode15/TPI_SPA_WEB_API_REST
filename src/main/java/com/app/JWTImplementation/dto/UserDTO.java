package com.app.JWTImplementation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    // Creacion
    
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 45, message = "Username must be between 4 and 45 characters")
    private String username;
    
    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters")
    private String password;
    
    @Size(max = 45, message = "Last name cannot exceed 45 characters")
    private String lastName;
    
    @NotBlank(message = "First name is required")
    @Size(max = 45, message = "First name cannot exceed 45 characters")
    private String firstName;
    
}
