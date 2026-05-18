package com.classicmodels.exception;

public class OrderUpdateException extends RuntimeException {
    public OrderUpdateException(Integer orderNumber, String reason) {
        super("Cannot update order " + orderNumber + ": " + reason);
    }
}