package com.app.JWTImplementation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    // Lectura

    private String status;
    private String message;
    private String token;

    // info del usuario logueado
    private Integer idUser;
    private String email;
    private String username;
    
}
