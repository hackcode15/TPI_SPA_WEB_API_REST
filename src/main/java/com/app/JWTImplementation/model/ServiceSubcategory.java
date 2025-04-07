package com.app.JWTImplementation.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tbl_service_subcategory")
public class ServiceSubcategory {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    // 1 subcategoría debe pertenecer a una categoría principal (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_main_id", nullable = false)
    private ServiceMainCategory serviceMainCategory;

    // 1 subcategoría puede tener muchos servicios (OneToMany)
    @OneToMany(mappedBy = "serviceSubcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceSpa> services;

}
