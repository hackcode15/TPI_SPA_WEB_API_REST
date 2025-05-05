package com.app.JWTImplementation.dto.projection;

import java.math.BigDecimal;

// Forma de acceder a informacion de los campos en entidades relacionadas
public interface ServiceSpaProjection {
    Integer getId();
    String getName();

    BigDecimal getPrice();

    String getDescription();
    String getCategoryName();
    Integer getDurationMinutes();
    Boolean getIsActive();
    Boolean getIsGroup();
}
