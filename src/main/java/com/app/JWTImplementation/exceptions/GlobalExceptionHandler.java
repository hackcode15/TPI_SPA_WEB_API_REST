package com.app.JWTImplementation.exceptions;

import java.util.HashMap;
//import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Excepcion para la entidad ServiceSpa
    @ExceptionHandler(ServiceSpaNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> hadleServiceSpaNotFoundException(ServiceSpaNotFoundException ex) {
        
        ApiResponse<String> error = new ApiResponse<>(
            "error",
            ex.getMessage(),
            null
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

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

    // Maneja errores de validación
    /* @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());
            
        ApiResponse<?> response = new ApiResponse<>(
            "error",
            "Validation failed",
            errors
        );
        
        return ResponseEntity.badRequest().body(response);
    } */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> hadleGlobalException(Exception ex) {
        
        ApiResponse<String> response = new ApiResponse<>(
            "error",
            "An unexpected error ocurred: " + ex.getMessage(),
            null
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
