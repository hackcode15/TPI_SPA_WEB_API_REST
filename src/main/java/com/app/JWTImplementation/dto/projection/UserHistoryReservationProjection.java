package com.app.JWTImplementation.dto.projection;

import java.time.LocalDateTime;

public interface UserHistoryReservationProjection {
    Integer getUserId();
    LocalDateTime getReserveDateTime();
    String getUserFullName();
    String getServiceName();
    LocalDateTime getServiceStartDatetime();
    LocalDateTime getServiceEndDatetime();
    String getReserveStatusName();
}
