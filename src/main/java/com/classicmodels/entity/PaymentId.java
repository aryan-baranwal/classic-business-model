package com.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public class PaymentId implements Serializable {

    @NotNull(message = "Customer number must not be null")
    @Column(name = "customerNumber")
    private Integer customerNumber;

    @NotBlank(message = "Check number must not be blank")
    @Column(name = "checkNumber")
    private String checkNumber;

    // Default Constructor
    public PaymentId() {
    }

    // Parameterized Constructor
    public PaymentId(Integer customerNumber, String checkNumber) {
        this.customerNumber = customerNumber;
        this.checkNumber = checkNumber;
    }

    // Getter for customerNumber
    public Integer getCustomerNumber() {
        return customerNumber;
    }

    // Setter for customerNumber
    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    // Getter for checkNumber
    public String getCheckNumber() {
        return checkNumber;
    }

    // Setter for checkNumber
    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PaymentId paymentId = (PaymentId) obj;
        if (customerNumber != null
                ? !customerNumber.equals(paymentId.customerNumber)
                : paymentId.customerNumber != null) {
            return false;
        }
        return checkNumber != null
                ? checkNumber.equals(paymentId.checkNumber)
                : paymentId.checkNumber == null;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int result = customerNumber != null ? customerNumber.hashCode() : 0;
        result = 31 * result + (checkNumber != null ? checkNumber.hashCode() : 0);
        return result;
    }
}