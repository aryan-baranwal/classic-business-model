package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;

public class HighRiskCustomerDto {

    @NotNull(message = "Customer number cannot be null")
    @PositiveOrZero(message = "Customer number cannot be negative")
    private Integer customerNumber;

    @NotBlank(message = "Customer name cannot be empty")
    private String customerName;

    @NotNull(message = "Credit limit cannot be null")
    @PositiveOrZero(message = "Credit limit cannot be negative")
    private BigDecimal creditLimit;

    @NotNull(message = "Total payments cannot be null")
    @PositiveOrZero(message = "Total payments cannot be negative")
    private BigDecimal totalPayments;

    @NotNull(message = "Remaining credit cannot be null")
    private BigDecimal remainingCredit;

    public HighRiskCustomerDto() {
    }

    public HighRiskCustomerDto(
            Integer customerNumber,
            String customerName,
            BigDecimal creditLimit,
            BigDecimal totalPayments,
            BigDecimal remainingCredit
    ) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.creditLimit = creditLimit;
        this.totalPayments = totalPayments;
        this.remainingCredit = remainingCredit;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(BigDecimal totalPayments) {
        this.totalPayments = totalPayments;
    }

    public BigDecimal getRemainingCredit() {
        return remainingCredit;
    }

    public void setRemainingCredit(BigDecimal remainingCredit) {
        this.remainingCredit = remainingCredit;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof HighRiskCustomerDto)) {
            return false;
        }

        HighRiskCustomerDto that =
                (HighRiskCustomerDto) o;

        return Objects.equals(
                customerNumber,
                that.customerNumber
        ) &&
                Objects.equals(
                        customerName,
                        that.customerName
                ) &&
                Objects.equals(
                        creditLimit,
                        that.creditLimit
                ) &&
                Objects.equals(
                        totalPayments,
                        that.totalPayments
                ) &&
                Objects.equals(
                        remainingCredit,
                        that.remainingCredit
                );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                customerNumber,
                customerName,
                creditLimit,
                totalPayments,
                remainingCredit
        );
    }
}