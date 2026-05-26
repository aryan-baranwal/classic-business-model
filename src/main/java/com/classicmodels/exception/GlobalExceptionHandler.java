package com.classicmodels.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
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

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentNotFoundException(
            PaymentNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Payment Not Found",
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
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
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

        String message = String.join(", ", errors.values());

        Map<String, Object> body = body(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                message,
                request.getRequestURI()
        );
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleBindException(
            BindException exception,
            HttpServletRequest request
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

        String message = String.join(", ", errors.values());

        Map<String, Object> body = body(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                message,
                request.getRequestURI()
        );
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {

        String message = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Validation Failed",
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(
            EntityNotFoundException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body(
                        HttpStatus.NOT_FOUND,
                        "Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(
            IllegalStateException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body(
                        HttpStatus.CONFLICT,
                        "Conflict",
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleMethodValidation(
            HandlerMethodValidationException exception,
            HttpServletRequest request
    ) {

        String message = exception.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Validation Failed",
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {

        String parameterName = exception.getName();
        Class<?> requiredType = exception.getRequiredType();
        boolean numericType = requiredType != null
                && (Number.class.isAssignableFrom(requiredType)
                || requiredType == int.class
                || requiredType == long.class
                || requiredType == short.class
                || requiredType == double.class
                || requiredType == float.class);
        String message = parameterName + " must be a valid "
                + (numericType ? "number" : "value");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Validation Failed",
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingRequestParameter(
            MissingServletRequestParameterException exception,
            HttpServletRequest request
    ) {

        String message = exception.getParameterName() + " is required";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Validation Failed",
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadableRequestBody(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {

        String message = "Request body contains invalid JSON or a value with the wrong type";
        Throwable cause = exception.getCause();

        if (cause instanceof JsonMappingException jsonMappingException
                && !jsonMappingException.getPath().isEmpty()) {
            String fieldName = jsonMappingException.getPath()
                    .get(jsonMappingException.getPath().size() - 1)
                    .getFieldName();

            if (fieldName != null) {
                message = fieldName + " contains a value with the wrong type";
            }
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Validation Failed",
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String, Object>> handleNumberFormat(
            NumberFormatException exception,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(
                        HttpStatus.BAD_REQUEST,
                        "Validation Failed",
                        "Numeric value must be a valid number",
                        request.getRequestURI()
                ));
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
