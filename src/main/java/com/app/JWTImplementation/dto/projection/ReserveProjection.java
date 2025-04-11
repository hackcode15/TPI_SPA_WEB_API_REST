package com.app.JWTImplementation.dto.projection;

import java.time.LocalDateTime;

import com.app.JWTImplementation.model.Reserve.StatusReserve;

public interface ReserveProjection {
    Integer getId();
    LocalDateTime getDateReserve();
    String getUserFullName();
    String getServiceName();
    LocalDateTime getScheduleStart();
    LocalDateTime getScheduleEnd();
    StatusReserve getStatus();
}
