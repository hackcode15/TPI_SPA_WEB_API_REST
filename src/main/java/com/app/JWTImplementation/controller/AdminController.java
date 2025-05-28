package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.admin.SaveNewUserDTO;
import com.app.JWTImplementation.dto.admin.SaveUserResponseDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    // Todos los endpoints que estan aqui requieren del rol ADMIN

    @Autowired
    private AdminService adminService;

    @PostMapping("/new-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> savedNewUser(@RequestBody SaveNewUserDTO userDetails) {

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "New user created successfully",
                adminService.creationNewUser(userDetails)
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }



}
