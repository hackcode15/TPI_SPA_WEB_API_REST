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
public class LoginRequest {

    // Validar entrada de datos

    @NotBlank // El campo username no debe estar vacio
    //@Size(min = 4, max = 45, message = "Username must be between 4 and 45 characters") // Longitud del username
    private String username;

    @NotBlank
    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters")
    private String password;

}
