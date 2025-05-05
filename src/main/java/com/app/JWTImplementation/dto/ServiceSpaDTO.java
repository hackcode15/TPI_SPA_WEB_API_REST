package com.app.JWTImplementation.dto;

import com.app.JWTImplementation.model.ServiceSpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSpaDTO {

    // Creacion

    //private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
    private Integer durationMinutes;
    private Boolean isActive;
    private Boolean isGroupService;

    public ServiceSpa toEntity() {
        return ServiceSpa.builder()
                .name(name)
                .price(price)
                .description(description)
                .categoryName(category)
                .durationMinutes(durationMinutes)
                .isActive(isActive)
                .isGroupService(isGroupService)
                .build();
    }

}
