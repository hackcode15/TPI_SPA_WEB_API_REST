package com.app.JWTImplementation.dto;

import com.app.JWTImplementation.model.Reserve;
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
    private Integer scheduleId;
    private Reserve.StatusReserve status;
}
