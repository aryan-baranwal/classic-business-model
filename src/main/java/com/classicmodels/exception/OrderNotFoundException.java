package com.classicmodels.exception;

// ─────────────────────────────────────────────────────────────────────────────
//  OrderNotFoundException
// ─────────────────────────────────────────────────────────────────────────────
public class OrderNotFoundException extends RuntimeException {

    private final Integer orderNumber;

    public OrderNotFoundException(Integer orderNumber) {
        super("Order not found with Order Number: " + orderNumber);
        this.orderNumber = orderNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }
}
