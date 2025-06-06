package com.app.JWTImplementation.service.impl;

import com.app.JWTImplementation.dto.request.LoginRequest;
import com.app.JWTImplementation.dto.request.RegisterRequest;
import com.app.JWTImplementation.dto.responses.AuthResponse;
import jakarta.mail.MessagingException;

public interface IAuthService {
    
    public AuthResponse login(LoginRequest request);
    public AuthResponse register(RegisterRequest request) throws MessagingException;

}   
