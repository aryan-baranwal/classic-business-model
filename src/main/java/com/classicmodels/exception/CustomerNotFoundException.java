package com.classicmodels.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Integer customerNumber) {
        super("Customer not found with ID: " + customerNumber);
    }

}