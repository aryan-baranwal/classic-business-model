package com.classicmodels.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OfficeNotFoundException.class)
    public String handleOfficeNotFoundException(
            OfficeNotFoundException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(
            MethodArgumentNotValidException ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                "Validation failed"
        );

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(
            Exception ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "error";
    }
}