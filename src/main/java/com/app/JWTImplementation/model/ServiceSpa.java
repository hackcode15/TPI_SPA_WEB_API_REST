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
@Table(name = "tbl_service")
public class ServiceSpa {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    
    @Column(nullable = false, length = 45)
    private String name;

    @Column(length = 150)
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    // 1 servicio debe pertenecer a una categoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    // Al elimiar un ServiceSpa se borrara sus horarios asociados
    // 1 servicio puede tener muchos horarios
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    /*
    Redundante
    // Al elimiar un ServiceSpa se borrara sus reservas asociados
    // 1 servicio puede estar en muchas reservas
    @OneToMany(mappedBy = "serviceSpa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserves;
    */

}
