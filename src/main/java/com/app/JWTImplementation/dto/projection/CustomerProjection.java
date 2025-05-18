package com.app.JWTImplementation.dto.projection;

import java.time.LocalDate;

public interface CustomerProjection {
    Integer getId();
    String getEmail();
    String getUsername();
    String getPassword();
    String getFirstName();
    String getLastName();
    String getPhone();
    LocalDate getBirthdate();
    String getRole();
}
