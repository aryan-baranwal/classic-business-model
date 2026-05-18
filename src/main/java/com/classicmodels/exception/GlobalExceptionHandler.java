package com.classicmodels.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── helpers ───────────────────────────────────────────────────────────────

    private Map<String, Object> body(HttpStatus status, String error,
                                     String message, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status",    status.value());
        body.put("error",     error);
        body.put("message",   message);
        body.put("path",      path);
        return body;
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  ORDER  exceptions
    // ═════════════════════════════════════════════════════════════════════════

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFound(
            OrderNotFoundException ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(HttpStatus.NOT_FOUND,
                        "Order Not Found",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleOrderAlreadyExists(
            OrderAlreadyExistsException ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body(HttpStatus.CONFLICT,
                        "Order Already Exists",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidOrderStatus(
            InvalidOrderStatusException ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(HttpStatus.BAD_REQUEST,
                        "Invalid Order Status",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(OrderUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleOrderUpdate(
            OrderUpdateException ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(HttpStatus.BAD_REQUEST,
                        "Order Update Failed",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  ORDER DETAIL  exceptions
    // ═════════════════════════════════════════════════════════════════════════

    @ExceptionHandler(OrderDetailNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderDetailNotFound(
            OrderDetailNotFoundException ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(HttpStatus.NOT_FOUND,
                        "Order Detail Not Found",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(OrderDetailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleOrderDetailAlreadyExists(
            OrderDetailAlreadyExistsException ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body(HttpStatus.CONFLICT,
                        "Order Detail Already Exists",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(OrderDetailUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleOrderDetailUpdate(
            OrderDetailUpdateException ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(HttpStatus.BAD_REQUEST,
                        "Order Detail Update Failed",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  FALLBACK
    // ═════════════════════════════════════════════════════════════════════════

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(
            Exception ex, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error",
                        ex.getMessage(),
                        request.getRequestURI()));
    }
}