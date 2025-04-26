package com.app.JWTImplementation.dto;

import com.app.JWTImplementation.model.ServiceSpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSpaDTO {

    // Creacion

    //private Integer id;
    private String name;
    private String description;
    private String category;
    private Integer durationMinutes;
    private Boolean isActive;
    private Boolean isGroupService;

    public ServiceSpa toEntity() {
        return ServiceSpa.builder()
                .name(name)
                .description(description)
                .categoryName(category)
                .durationMinutes(durationMinutes)
                .isActive(isActive)
                .isGroupService(isGroupService)
                .build();
    }

}
