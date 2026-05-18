package com.classicmodels.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> body(
            HttpStatus status,
            String error,
            String message,
            String path
    ) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", path);

        return body;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFoundException(
            EmployeeNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Employee Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(OfficeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOfficeNotFoundException(
            OfficeNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Office Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(
            ProductNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Product Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(
            CustomerNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Customer Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFound(
            OrderNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Order Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleOrderAlreadyExists(
            OrderAlreadyExistsException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body(
                        HttpStatus.CONFLICT,
                        "Order Already Exists",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidOrderStatus(
            InvalidOrderStatusException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Invalid Order Status",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(OrderUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleOrderUpdate(
            OrderUpdateException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Order Update Failed",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(OrderDetailNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderDetailNotFound(
            OrderDetailNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Order Detail Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(OrderDetailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleOrderDetailAlreadyExists(
            OrderDetailAlreadyExistsException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body(
                        HttpStatus.CONFLICT,
                        "Order Detail Already Exists",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(OrderDetailUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleOrderDetailUpdate(
            OrderDetailUpdateException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Order Detail Update Failed",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
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
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }
}