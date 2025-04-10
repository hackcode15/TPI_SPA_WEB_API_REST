package com.app.JWTImplementation.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tbl_service_category")
public class ServiceCategory {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(length = 150)
    private String description;

    @Builder.Default
    @Column(name = "is_group_service", nullable = false)
    private Boolean isGroupService = false;

    // 1 subcategoría puede tener muchos servicios (OneToMany)
    // Al elimiar un ServiceSubcategory se borrara sus ServiceSpa asociados
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceSpa> services;

}
