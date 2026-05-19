package com.classicmodels.exception;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException(Integer orderNumber) {
        super("Order already exists with orderNumber: " + orderNumber);
    }
}