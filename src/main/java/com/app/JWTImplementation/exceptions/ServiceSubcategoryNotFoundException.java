package com.app.JWTImplementation.exceptions;

public class ServiceSubcategoryNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ServiceSubcategoryNotFoundException(Integer id) {
        super("Service Subcategory not found with ID: " + id);
    }

    public ServiceSubcategoryNotFoundException(String message) {
        super(message);
    }

}
