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
public class ServiceSpaInfoDTO {

    // Forma de mostrar la informacion

    private Integer id;
    private String name;
    private String description;
    private String category;
    private Integer durationMinutes;
    private Boolean isActive;
    private String type;

    public static ServiceSpaInfoDTO fromServiceSpa(ServiceSpa service) {
        return ServiceSpaInfoDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .category(service.getCategoryName())
                .durationMinutes(service.getDurationMinutes())
                .isActive(service.getIsActive())
                .type(service.getIsGroupService() ? "Group" : "Individual")
                .build();
    }

}
