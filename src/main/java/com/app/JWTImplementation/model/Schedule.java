package com.app.JWTImplementation.model;

import java.time.LocalDateTime;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "tbl_schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime startDatetime;
    
    @Column(nullable = false)
    private LocalDateTime endDatetime;
    
    @Builder.Default
    @Column(nullable = false)
    private Integer maxCapacity = 1;
    
    @Builder.Default
    @Column(nullable = false)
    private Integer currentCapacity = 0;
    
    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    // 1 horario pertenece a un servicio (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceSpa service;
    
    // 1 horario puede estar en muchas reservas (OneToMany)
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserves;

    // Validación personalizada
    @PrePersist
    @PreUpdate
    private void validate() {
        if (endDatetime.isBefore(startDatetime)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la de inicio");
        }
    }
}
