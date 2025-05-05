package com.app.JWTImplementation.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface UserHistoryReservationProjection {
    Integer getUserId();
    LocalDateTime getReserveDateTime();
    String getUserFullName();
    String getServiceName();

    BigDecimal getServicePrice();

    LocalDateTime getServiceStartDatetime();
    LocalDateTime getServiceEndDatetime();
    String getReserveStatusName();
}
