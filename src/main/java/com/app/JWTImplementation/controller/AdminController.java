package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.admin.SaveNewUserDTO;
import com.app.JWTImplementation.dto.admin.SaveUserResponseDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.dto.responses.TotalIncomeHistory;
import com.app.JWTImplementation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // Todos los endpoints que estan aqui requieren del rol ADMIN

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new-user")
    public ResponseEntity<ApiResponse<String>> savedNewUser(@RequestBody SaveNewUserDTO userDetails) {

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "New user created successfully",
                adminService.creationNewUser(userDetails)
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/total-income")
    @ResponseBody
    public ResponseEntity<ApiResponse<TotalIncomeHistory>> getTotalIncomeHistory(
            @RequestParam("startDate")LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {

        ApiResponse<TotalIncomeHistory> response = new ApiResponse<>(
                "Success",
                "Retrived total income history successfully",
                adminService.getTotalIncomeHistory(startDate, endDate)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
