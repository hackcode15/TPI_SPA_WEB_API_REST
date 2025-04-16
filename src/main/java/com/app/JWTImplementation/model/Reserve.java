package com.app.JWTImplementation.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_reserve")
public class Reserve {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    
    @CreationTimestamp
    private LocalDateTime dateReserve;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", nullable = false)
    private StatusReserve status = StatusReserve.CONFIRMED;

    // 1 reserva pertenece a un usuario (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // 1 reserva pertenece a un horario (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // Metodo helper para acceder al servicio indirectamente
    public ServiceSpa getService() {
        return this.schedule.getService();
    }

    // Validación de integridad
    @PrePersist
    @PreUpdate
    private void validate() {
        if (schedule == null) {
            throw new IllegalStateException("La reserva debe tener un horario asociado");
        }
        if (schedule.getCurrentCapacity() >= schedule.getMaxCapacity()) {
            throw new IllegalStateException("No hay capacidad disponible en este horario");
        }
    }

    public enum StatusReserve {
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

}
