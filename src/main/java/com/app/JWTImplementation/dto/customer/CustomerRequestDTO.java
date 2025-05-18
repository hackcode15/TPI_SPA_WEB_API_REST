package com.app.JWTImplementation.dto.customer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDTO {
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthdate;
}
