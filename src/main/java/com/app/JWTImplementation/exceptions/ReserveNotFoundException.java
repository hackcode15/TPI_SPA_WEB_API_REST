package com.app.JWTImplementation.exceptions;

public class ReserveNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ReserveNotFoundException(Integer id) {
        super("Reserve not found with ID: " + id);
    }

    public ReserveNotFoundException(String message) {
        super(message);
    }

}
