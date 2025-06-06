package com.app.JWTImplementation.dto;

import com.app.JWTImplementation.model.Reserve;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDTO {

    private Integer userId;
    private Integer scheduleId; // Para horarios existentes
    private LocalDateTime selectedTime; // Para nuevos horarios
    private Integer serviceId; // Para nuevos horarios
    private Integer professionalId;

}
