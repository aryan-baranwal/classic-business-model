package com.classicmodels.exception;

public class OrderDetailNotFoundException extends RuntimeException {

    private final Integer orderNumber;
    private final String  productCode;

    public OrderDetailNotFoundException(Integer orderNumber, String productCode) {
        super("Order detail not found for orderNumber=" + orderNumber + ", productCode=" + productCode);
        this.orderNumber = orderNumber;
        this.productCode = productCode;
    }

    public Integer getOrderNumber() { return orderNumber; }
    public String  getProductCode() { return productCode; }
}