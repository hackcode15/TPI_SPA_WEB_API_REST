package com.app.JWTImplementation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "tbl_professional")
public class Professional extends User {
    private String specialty;
    private String license;
    @Column(name = "photo_url")
    private String photoUrl;
}
