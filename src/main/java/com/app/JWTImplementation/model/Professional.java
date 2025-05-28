package com.app.JWTImplementation.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "tbl_professional")
public class Professional extends User {

    @Column(nullable = false)
    private String specialty;

    @Column(nullable = false)
    private String license;

    @Column(name = "photo_url")
    private String photoUrl;

    // 1 profesional puede estar en muchas reservas
    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserves;

}
