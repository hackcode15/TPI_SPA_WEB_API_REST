package com.app.JWTImplementation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryReservationResponse {
    private String title;
    private Integer countReservation;
    private BigDecimal totalPrice;
    private List<UserReservationHistoryResponse> reservations;
}
