package com.app.JWTImplementation.exceptions;

public class ServiceSpaNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ServiceSpaNotFoundException(Integer id) {
        super("Service Spa not found with ID: " + id);
    }

    public ServiceSpaNotFoundException(String message) {
        super(message);
    }

}
