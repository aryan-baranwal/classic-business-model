package com.classicmodels.exception;

public class OrderDetailUpdateException extends RuntimeException {
    public OrderDetailUpdateException(Integer orderNumber, String productCode, String reason) {
        super("Cannot update order detail for orderNumber: " + orderNumber
                + ", productCode: " + productCode + " — " + reason);
    }
}