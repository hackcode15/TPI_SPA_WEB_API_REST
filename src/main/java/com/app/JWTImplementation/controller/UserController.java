package com.app.JWTImplementation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.UserDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.dto.responses.UserResponse;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.service.UserService;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuario", description = "Controlador para los Usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    // ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA ADMINISTRADORES Y DESARROLLADORES░░░░░░░░░░░░░░░░░
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @GetMapping("/username/{username}")
    @ResponseBody
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@PathVariable String username) {

        log.info("Buscando usuario por username: {}", username);

        UserResponse user = userService.findUserByUsername(username);

        log.debug("Usuario encontrado: {}", user);

        ApiResponse<UserResponse> response = new ApiResponse<>(
                "Success",
                "User retrived successfully",
                user
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA ADMINISTRADORES Y DESARROLLADORES░░░░░░░░░░░░░░░░░
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @GetMapping("/list")
    @ResponseBody
    @Operation(
            summary = "Ver todos los usuarios",
            description = "Lista todos los usuarios con todos su datos",
            tags = {"Usuario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Lista de usuarios recuperados con exito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        /*
        List<User> users = userService.findAllUsers();

        List<UserResponse> userResponses = users.stream()
                .map(UserResponse::fromUser)
                .toList(); // Valido  a partir de Java 16+ (mejor que Collectors.toList())
        */

        /*List<User> users = userService.findAllUsers();

        List<UserResponse> userResponses = users.stream()
                .map(user -> {

                    UserResponse userDTO = UserResponse.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .createAt(user.getCreateAt())
                            .updateAt(user.getUpdateAt())
                            .roleName(user.getRole())
                            .build();

                    return userDTO;

                }).collect(Collectors.toList());*/

        ApiResponse<List<UserResponse>> response = new ApiResponse<>(
                "Success",
                "Users retrived succesfully",
                userService.findAllUsers()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA ADMINISTRADORES Y DESARROLLADORES░░░░░░░░░░░░░░░░░
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @GetMapping("/{id}")
    @ResponseBody
    @Operation(
            summary = "Mostrar Usuario por su ID",
            description = "Busca el usuario por el ID y muestra toda su información",
            tags = {"Usuario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Usuario recuperado con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("id") Integer id) {

        /*User user = userService.findUserById(id);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .roleName(user.getRole())
                .build();*/

        ApiResponse<UserResponse> response = new ApiResponse<>(
                "Success",
                "User successfully recovered",
                userService.findUserById(id)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA ADMINISTRADORES Y DESARROLLADORES░░░░░░░░░░░░░░░░░
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @PostMapping("/new")
    @ResponseBody
    @Operation(
            summary = "Nuevo Usuario",
            description = "Creación de un nuevo Usuario",
            tags = {"Usuario"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de creación con usuario, contraseña, nombre y apellido. Rol de usuario por defecto",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserDTO.class
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Usuario creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserResponse>> newUser(@Valid @RequestBody UserDTO userDetails) {

        /*User newUserCreated = userService.saveUser(userDetails);

        UserResponse userResponse = UserResponse.builder()
                .id(newUserCreated.getId())
                .username(newUserCreated.getUsername())
                .password(newUserCreated.getPassword())
                .firstName(newUserCreated.getFirstName())
                .lastName(newUserCreated.getLastName())
                .createAt(newUserCreated.getCreateAt())
                .updateAt(newUserCreated.getUpdateAt())
                .roleName(newUserCreated.getRole())
                .build();*/

        ApiResponse<UserResponse> response = new ApiResponse<>(
                "Success",
                "User created successfully",
                userService.saveUser(userDetails)
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    // patch - update partial
    // @PatchMapping("/update-partial/{id}")

    // ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA ADMINISTRADORES Y DESARROLLADORES░░░░░░░░░░░░░░░░░
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @PutMapping("/update/{id}")
    @ResponseBody
    @Operation(
            summary = "Editar Usuario por su ID",
            description = "Actualiza todos los datos del Usuario por ID si existe",
            tags = {"Usuario"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de modificacion con id, usuario, contraseña, nombre y apellido",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserDTO.class
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Usuario actualizado con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UserDTO userDetails) {

        /*User user = userService.updateUserById(id, userDetails);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .updateAt(LocalDateTime.now())
                .roleName(user.getRole())
                .build();*/

        ApiResponse<UserResponse> response = new ApiResponse<>(
                "Success", // modificado
                "Updated user successfully",
                userService.updateUserById(id, userDetails)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA ADMINISTRADORES Y DESARROLLADORES░░░░░░░░░░░░░░░░░
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Borrar Usuario",
            description = "ELimina el usuario por ID",
            tags = {"Usuario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Usuario eliminado con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable("id") Integer id) {

        userService.deleteUserById(id);

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "Deleted user successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
