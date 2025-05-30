package com.app.JWTImplementation.service;

import java.time.LocalDateTime;

import com.app.JWTImplementation.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.JWT.JwtService;
import com.app.JWTImplementation.dto.request.LoginRequest;
import com.app.JWTImplementation.dto.request.RegisterRequest;
import com.app.JWTImplementation.dto.responses.AuthResponse;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.model.User.Role;
import com.app.JWTImplementation.repository.UserRepository;
import com.app.JWTImplementation.service.impl.IAuthService;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        //UserDetails user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();

        //String token = jwtService.getToken(user);



        // Podria utilizar solo el UserDetails para obtener el username
        // Otro paso mas para obtener el id y username
        User userLogin = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not foud whit username: " + request.getUsername()));

        String token = jwtService.getToken(userLogin, userLogin.getId());

        return AuthResponse.builder()
                .status("Success")
                .message("You have successfully logged in")
                .idUser(userLogin.getId())
                .email(userLogin.getEmail())
                .username(userLogin.getUsername())
                .token(token)
                .build();

    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createAt(LocalDateTime.now())
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        String token = jwtService.getToken(user, user.getId());

        return AuthResponse.builder()
                .status("Success")
                .message("User successfully registered")
                .idUser(user.getId()) // nuevo -> obtener el id
                .email(user.getEmail())
                .username(user.getUsername()) // nuevo -> obtener el username
                //.token(jwtService.getToken(user))
                .token(token)
                .build();

    }

}
