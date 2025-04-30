package com.app.JWTImplementation.exceptions;


import java.util.HashMap;
//import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.JWTImplementation.dto.responses.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Excepcion para la entidad User
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> hadleUserNotFoundException(UserNotFoundException ex) {

        ApiResponse<String> error = new ApiResponse<>(
            "error",
            ex.getMessage(),
            null
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    // ServiceSpa
    @ExceptionHandler(ServiceSpaNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> hadleServiceSpaNotFoundException(ServiceSpaNotFoundException ex) {
        
        ApiResponse<String> error = new ApiResponse<>(
            "error",
            ex.getMessage(),
            null
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    // Schedule
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> hadleScheduleNotFoundException(ScheduleNotFoundException ex) {
        
        ApiResponse<String> error = new ApiResponse<>(
            "error",
            ex.getMessage(),
            null
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    // Reserve
    @ExceptionHandler(ReserveNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> hadleReserveNotFoundException(ReserveNotFoundException ex) {
        
        ApiResponse<String> error = new ApiResponse<>(
            "error",
            ex.getMessage(),
            null
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    // Reservas invalidas
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidReservationException(InvalidReservationException ex) {

        ApiResponse<String> error = new ApiResponse<>(
                "error",
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    // Excepciones genericas comunes
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
    
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
            .forEach(error -> errors.put(
                error.getField(), 
                error.getDefaultMessage()
            ));

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
            "error",
            "Validation failed",
            errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> hadleGlobalException(Exception ex) {
        
        ApiResponse<String> response = new ApiResponse<>(
            "error",
            "An unexpected error ocurred: " + ex.getMessage(),
            null
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // Excepcion para la negacion de acceso
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> hadleAccessDeniedException(AccessDeniedException ex) {

        ApiResponse<String> response = new ApiResponse<>(
                "error",
                "Acceso denegado: No tienes los permisos necesarios",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }

}
