package com.app.JWTImplementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamSource;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRegisterDTO {
    private String addressee;
    private String subjet;
    private String message;
}
