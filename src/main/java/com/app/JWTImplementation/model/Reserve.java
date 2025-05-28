package com.app.JWTImplementation.model;

import java.time.LocalDateTime;

import com.app.JWTImplementation.exceptions.InvalidReservationException;
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

    // 1 reserva pertenece a un profesional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;

    // Metodo helper para acceder al servicio indirectamente
    public ServiceSpa getService() {
        return this.schedule.getService();
    }

    public enum StatusReserve {
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

}
