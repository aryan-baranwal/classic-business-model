package com.classicmodels.exception;

public class OrderDetailAlreadyExistsException extends RuntimeException {
    public OrderDetailAlreadyExistsException(Integer orderNumber, String productCode) {
        super("Order detail already exists for orderNumber: " + orderNumber
                + " and productCode: " + productCode);
    }
}