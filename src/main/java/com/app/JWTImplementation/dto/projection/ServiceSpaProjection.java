package com.app.JWTImplementation.dto.projection;

// Forma de acceder a informacion de los campos en entidades relacionadas
public interface ServiceSpaProjection {
    Integer getId();
    String getName();
    String getDescription();
    Integer getDurationMinutes();
    Boolean getIsActive();
    String getCategoryName();
    Boolean getIsGroup();
}
