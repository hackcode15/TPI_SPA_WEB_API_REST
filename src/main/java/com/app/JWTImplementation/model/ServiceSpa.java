package com.app.JWTImplementation.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_service")
public class ServiceSpa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(length = 100)
    private String description;

    @OneToMany(targetEntity = Reserve.class, mappedBy = "service", fetch = FetchType.LAZY)
    private List<Reserve> reserves;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private StatusEnum status;

    public enum StatusEnum {
        ACTIVE,
        INACTIVE
    }

}
