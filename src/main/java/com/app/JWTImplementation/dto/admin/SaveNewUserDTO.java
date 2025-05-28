package com.app.JWTImplementation.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveNewUserDTO {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 45, message = "Username must be between 4 and 45 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 45, message = "Password must be between 4 and 45 characters")
    private String password;

    @NotBlank(message = "First Name is required")
    @Size(min = 4, max = 45, message = "Username must be between 4 and 45 characters")
    private String firstName;
    private String lastName;
    private String role;

    // customer
    @NotBlank(message = "Phone is required")
    private String phone;
    private LocalDate birthdate;

    // professional
    private String specialty;
    private String license;

    @NotBlank(message = "Photo URL is required")
    private String photoUrl;

}
