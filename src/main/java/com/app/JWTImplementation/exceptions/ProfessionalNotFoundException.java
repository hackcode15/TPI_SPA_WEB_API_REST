package com.app.JWTImplementation.exceptions;

public class ProfessionalNotFoundException extends RuntimeException {
    public ProfessionalNotFoundException(String message) {
        super(message);
    }
    public ProfessionalNotFoundException(Integer id) {
        super("Professional not found whit id: " + id);
    }
}
