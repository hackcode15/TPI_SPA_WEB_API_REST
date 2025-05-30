package com.app.JWTImplementation.dto.responses;

import com.app.JWTImplementation.dto.projection.UserHistoryReservationProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReservationHistoryResponse {

    private Integer userId;
    private Integer reserveId;
    private LocalDate reserveDate;
    private String userFullName;
    private String serviceName;

    private BigDecimal servicePrice;

    private LocalDate serviceStartDate;
    private LocalTime serviceStartTime;
    private LocalTime serviceEndTime;
    private String reserveStatusName;

    public static UserReservationHistoryResponse fromUserReservationHistory(UserHistoryReservationProjection reservationHistory) {
        return UserReservationHistoryResponse.builder()
                .userId(reservationHistory.getUserId())
                .reserveId(reservationHistory.getReserveId())
                .reserveDate(reservationHistory.getReserveDateTime().toLocalDate())
                .userFullName(reservationHistory.getUserFullName())
                .serviceName(reservationHistory.getServiceName())
                .servicePrice(reservationHistory.getServicePrice()) //
                .serviceStartDate(reservationHistory.getServiceStartDatetime().toLocalDate())
                .serviceStartTime(reservationHistory.getServiceStartDatetime().toLocalTime())
                .serviceEndTime(reservationHistory.getServiceEndDatetime().toLocalTime())
                .reserveStatusName(reservationHistory.getReserveStatusName())
                .build();
    }

}
