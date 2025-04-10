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
@Table(name = "tbl_service_main_category")
public class ServiceMainCategory {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(length = 150)
    private String description;

    @Builder.Default
    @Column(name = "is_group_service")
    private Boolean isGroupService = false;

    // 1 categoría principal puede tener muchas subcategorías (OneToMany)
    // Al elimiar un ServiceMainCategory se borrara sus ServiceSubcategory asociados
    @OneToMany(mappedBy = "serviceMainCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceSubcategory> subcategories;

}
