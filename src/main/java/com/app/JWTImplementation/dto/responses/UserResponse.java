package com.app.JWTImplementation.dto.responses;

import java.time.LocalDateTime;

import com.app.JWTImplementation.model.User;
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
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Role roleName;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .roleName(user.getRole())
                .build();
    }

}
