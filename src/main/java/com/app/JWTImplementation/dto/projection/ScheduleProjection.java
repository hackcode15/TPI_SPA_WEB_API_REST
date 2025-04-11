package com.app.JWTImplementation.dto.projection;

import java.time.LocalDateTime;

public interface ScheduleProjection {

    Integer getId();
    LocalDateTime getStartDatetime();
    LocalDateTime getEndDatetime();
    Integer getMaxCapacity();
    Integer getCurrentCapacity();
    Boolean getIsActive();
    String getServiceName();

}
