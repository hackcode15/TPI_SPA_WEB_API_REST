package com.app.JWTImplementation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // Creacion

    @NotBlank
    private String email;

    @NotBlank // El campo username no debe estar vacio
    @Size(min = 4, max = 45, message = "Username must be between 4 and 45 characters") // Longitud del username
    private String username;

    @NotBlank 
    @Size(min = 4, max = 45, message = "Password must be between 4 and 45 characters") 
    private String password;

    @NotBlank
    @Size(min = 4, max = 45, message = "First Name must be between 4 and 45 characters") 
    private String firstName;
    
    @Size(min = 4, max = 45, message = "Last Name must be between 4 and 45 characters") 
    private String lastName;

}
