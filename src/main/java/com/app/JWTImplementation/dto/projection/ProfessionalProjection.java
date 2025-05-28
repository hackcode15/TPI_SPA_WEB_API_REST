package com.app.JWTImplementation.dto.projection;

import java.time.LocalDate;

public interface ProfessionalProjection {
    Integer getId();
    String getEmail();
    String getUsername();
    String getPassword();
    String getFirstName();
    String getLastName();
    String getSpecialty();
    String getLicense();
    String getPhotoUrl();
    String getRole();
}
