package com.app.JWTImplementation.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface InvoiceProjection {
    Integer getId();
    String getInvoiceNumber();
    LocalDateTime getIssueDate();
    String getPaymentMethod();
    String getServiceName();
    BigDecimal getSubtotal();
    BigDecimal getTaxAmount();
    BigDecimal getTotal();
    String getCustomerName();
    String getCustomerEmail();
    String getCustomerIdentification();
    Integer getReserveId();
    Boolean getIsPaid();
}
