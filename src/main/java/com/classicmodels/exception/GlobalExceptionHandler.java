package com.classicmodels.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(
            EmployeeNotFoundException exception
    ) {

        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(OfficeNotFoundException.class)
    public ResponseEntity<String> handleOfficeNotFoundException(
            OfficeNotFoundException exception
    ) {

        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(
            ProductNotFoundException exception
    ) {

        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(
            CustomerNotFoundException exception
    ) {

        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(
            Exception exception
    ) {

        return new ResponseEntity<>(
                "Internal Server Error: " + exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}