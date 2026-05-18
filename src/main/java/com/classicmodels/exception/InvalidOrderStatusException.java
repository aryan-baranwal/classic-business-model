package com.classicmodels.exception;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException(String status) {
        super("Invalid order status: '" + status
                + "'. Allowed values: In Process, Shipped, Cancelled, Resolved, On Hold, Disputed");
    }
}