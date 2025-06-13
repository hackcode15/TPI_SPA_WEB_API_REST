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
public class TotalIncomeByService {
    private Integer idService;
    private String serviceName;
    private String serviceCategoryName;
    private String type;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;
    private List<ReservesTotalIncomes> reserves;
}
