package com.app.JWTImplementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDTO {

    // datos de la facturacion
    private Integer id;
    private String invoiceNumber;
    private LocalDate issueDate;
    private String paymentMethod;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal total;

    // datos del cliente
    private String customerName;
    private String customerEmail;
    private String customerIdentification;

    private String serviceName; // informacion del servicio solicitado

    private Integer reserveId; // informacion de la reserva

    private String isPaid;

}
