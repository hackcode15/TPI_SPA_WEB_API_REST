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
@Tag(name = "Autenticación", description = "Controlador para la Autenticación")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Inicio de Sesión del Usuario",
            description = "Autenticar un usuario y devolver el token de autenticación",
            tags = {"Autenticación"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de autenticación con nombre de usuario y contraseña",
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
                            description = "Has iniciado sesión correctamente",
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
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(
            summary = "Registro de Usuario",
            description = "Registrar un usuario y devolver el token de autenticación",
            tags = {"Autenticación"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de registro con usuario, contraseña, nombre y apellido. Rol de usuario por defecto",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = RegisterRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario registrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AuthResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
