package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private LocalDate paymentStartDate;

    private LocalDate paymentEndDate;

    private String dateRange;

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

    public HighRiskCustomerDto(
            Integer customerNumber,
            String customerName,
            BigDecimal creditLimit,
            BigDecimal totalPayments,
            BigDecimal remainingCredit,
            LocalDate paymentStartDate,
            LocalDate paymentEndDate
    ) {
        this(
                customerNumber,
                customerName,
                creditLimit,
                totalPayments,
                remainingCredit
        );
        this.paymentStartDate = paymentStartDate;
        this.paymentEndDate = paymentEndDate;
        this.dateRange = formatDateRange(
                paymentStartDate,
                paymentEndDate
        );
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

    public LocalDate getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(LocalDate paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
        this.dateRange = formatDateRange(
                this.paymentStartDate,
                this.paymentEndDate
        );
    }

    public LocalDate getPaymentEndDate() {
        return paymentEndDate;
    }

    public void setPaymentEndDate(LocalDate paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
        this.dateRange = formatDateRange(
                this.paymentStartDate,
                this.paymentEndDate
        );
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
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
                ) &&
                Objects.equals(
                        paymentStartDate,
                        that.paymentStartDate
                ) &&
                Objects.equals(
                        paymentEndDate,
                        that.paymentEndDate
                ) &&
                Objects.equals(
                        dateRange,
                        that.dateRange
                );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                customerNumber,
                customerName,
                creditLimit,
                totalPayments,
                remainingCredit,
                paymentStartDate,
                paymentEndDate,
                dateRange
        );
    }

    private static String formatDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate == null && endDate == null) {
            return "No payments recorded";
        }
        if (Objects.equals(startDate, endDate)) {
            return String.valueOf(startDate);
        }
        return startDate + " to " + endDate;
    }
}
