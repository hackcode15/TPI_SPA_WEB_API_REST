package com.app.JWTImplementation.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.JWTImplementation.model.Reserve.StatusReserve;

public interface ReserveProjection {
    Integer getId();
    LocalDateTime getDateReserve();
    String getUserFullName();
    String getServiceName();
    BigDecimal getServicePrice();
    LocalDateTime getScheduleStart();
    LocalDateTime getScheduleEnd();
    String getProfessionalName();
    StatusReserve getStatus();
}
