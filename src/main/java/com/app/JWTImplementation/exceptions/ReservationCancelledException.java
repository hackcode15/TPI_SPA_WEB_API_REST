package com.app.JWTImplementation.exceptions;

public class ReservationCancelledException extends RuntimeException {
    public ReservationCancelledException(String message) {
        super(message);
    }
}
