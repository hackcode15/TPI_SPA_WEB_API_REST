package com.app.JWTImplementation.dto.responses;

import java.time.LocalDateTime;

import com.app.JWTImplementation.model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    // Lectura
    
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Role roleName;

}
