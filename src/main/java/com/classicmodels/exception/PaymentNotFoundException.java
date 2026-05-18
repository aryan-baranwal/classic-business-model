package com.classicmodels.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(Integer customerNumber, String checkNumber) {
        super("Payment not found for customerNumber: "
                + customerNumber
                + " and checkNumber: "
                + checkNumber);
    }

}