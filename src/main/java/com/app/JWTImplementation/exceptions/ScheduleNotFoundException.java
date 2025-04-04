package com.app.JWTImplementation.exceptions;

public class ScheduleNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ScheduleNotFoundException(Integer id) {
        super("Schedule not found with ID: " + id);
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }

}
