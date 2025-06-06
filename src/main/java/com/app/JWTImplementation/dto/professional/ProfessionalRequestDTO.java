package com.app.JWTImplementation.dto.professional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessionalRequestDTO {
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String specialty;
    private String license;
    private String photoUrl;
}
