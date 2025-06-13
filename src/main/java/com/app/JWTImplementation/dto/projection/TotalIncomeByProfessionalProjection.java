package com.app.JWTImplementation.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TotalIncomeByProfessionalProjection {
    Integer getIdReserve();
    LocalDateTime getDateReserve();
    BigDecimal getPrice();
    String getCustomerFullName();
    LocalDateTime getDateTurn();
    String getServiceName();
    String getStatusReserve();
}
