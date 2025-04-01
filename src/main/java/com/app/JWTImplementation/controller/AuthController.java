package com.app.JWTImplementation.controller;

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
public class AuthController {
    
    private final AuthService authService;

    /* @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    } */

    /* @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    } */

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        
        // El metodo login del authService retorna un DTO AuthResponse
        AuthResponse response = authService.login(request);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        
        // El metodo register del authService retorna un DTO AuthResponse
        AuthResponse response = authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
