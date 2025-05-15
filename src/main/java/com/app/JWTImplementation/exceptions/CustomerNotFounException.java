package com.app.JWTImplementation.exceptions;

public class CustomerNotFounException extends RuntimeException {
    public CustomerNotFounException(Integer id) {
        super("Customer not found with ID: " + id);
    }
    public CustomerNotFounException(String message) {
        super(message);
    }
}
