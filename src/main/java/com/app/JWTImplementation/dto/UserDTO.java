package com.app.JWTImplementation.dto;

import com.app.JWTImplementation.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    // Creacion

    @NotBlank(message = "email is required")
    private String email;

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

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(this.email)
                .username(this.username)
                .password(passwordEncoder.encode(this.password)) // Encriptación aquí
                .firstName(this.firstName)
                .lastName(this.lastName)
                .createAt(LocalDateTime.now()) // Fecha actual
                .role(User.Role.CUSTOMER) // Valor por defecto
                .build();
    }
    
}
