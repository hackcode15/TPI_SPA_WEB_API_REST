package com.app.JWTImplementation.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveUserResponseDTO {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    // customer
    private String phone;
    private LocalDate birthdate;

    // professional
    private String specialty;
    private String license;
    private String photoUrl;

}
