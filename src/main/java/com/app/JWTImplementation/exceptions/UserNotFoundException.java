package com.app.JWTImplementation.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Integer id) {
        super("User not found with ID: " + id);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
