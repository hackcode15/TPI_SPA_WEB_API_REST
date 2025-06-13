package com.app.JWTImplementation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "tbl_customer")
public class Customer extends User {

    @Column(nullable = false)
    private String phone;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

}
