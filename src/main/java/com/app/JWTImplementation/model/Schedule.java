package com.app.JWTImplementation.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "tbl_schedule")
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime hour;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean available = true;

    @OneToOne(targetEntity = Reserve.class, mappedBy = "schedule")
    private Reserve reserve;

}
