package com.app.JWTImplementation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.request.LoginRequest;
import com.app.JWTImplementation.dto.request.RegisterRequest;
import com.app.JWTImplementation.dto.responses.AuthResponse;
import com.app.JWTImplementation.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Controller for Authentication")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Login User",
            description = "Authenticate a user and return the authentication token along with user details",
            tags = {"Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication request with username and password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoginRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "You have successfully logged in",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AuthResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        
        // El metodo login del authService retorna un DTO AuthResponse
        AuthResponse response = authService.login(request);

        //return new ResponseEntity<>(response, HttpStatus.OK);

        // nuevo
        return ResponseEntity.ok()
            .header("Access-Control-Allow-Origin", "http://127.0.0.1:5500")
            .header("Access-Control-Allow-Credentials", "true")
            .body(response);

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        
        // El metodo register del authService retorna un DTO AuthResponse
        AuthResponse response = authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
