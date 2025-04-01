package com.app.JWTImplementation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.UserUpdateDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.dto.responses.UserResponse;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        
        List<User> users = userService.findAllUsers();

        List<UserResponse> userResponses = users.stream()
            .map(user -> {
                
                UserResponse userDTO = UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .country(user.getCountry())
                    .roleName(user.getRole())
                    .build();

                return userDTO;

            }).collect(Collectors.toList());

        ApiResponse<List<UserResponse>> response = new ApiResponse<>(
            "Success",
            "Users retrived succesfully",
            userResponses
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("id") Integer id) {
        
        User user = userService.findUserById(id);

        UserResponse userResponse = UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .country(user.getCountry())
            .roleName(user.getRole())
            .build();

        ApiResponse<UserResponse> response = new ApiResponse<>(
            "Success",
            "User successfully recovered",
            userResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // post - save user
    // @PostMapping("/new")

    // patch - update partial
    // @PatchMapping("/update-partial/{id}")

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(
        @PathVariable("id") Integer id, 
        @Valid @RequestBody UserUpdateDTO userDetails) {
    
        User user = userService.updateUserById(id, userDetails);

        UserResponse userResponse = UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .country(user.getCountry())
            .roleName(user.getRole())
            .build();

        ApiResponse<UserResponse> response = new ApiResponse<>(
            "Seccess",
            "Updated user successfully",
            userResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
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
