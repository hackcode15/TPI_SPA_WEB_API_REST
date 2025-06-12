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
public class TotalIncomeByProfessional {

    private Integer idProfessional;
    private String nameProfessional;
    private String specialtyProfessional;
    private Integer count;
    private BigDecimal totalPrice;
    private List<ReservesByProfessional> reserves;

}
