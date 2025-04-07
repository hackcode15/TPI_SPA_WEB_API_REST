package com.app.JWTImplementation.exceptions;

public class ServiceMainCategoryNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ServiceMainCategoryNotFoundException(Integer id) {
        super("Service Main Category not found with ID: " + id);
    }

    public ServiceMainCategoryNotFoundException(String message) {
        super(message);
    }

}
