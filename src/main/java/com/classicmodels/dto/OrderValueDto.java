package com.classicmodels.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.Objects;

public class OrderValueDto {

    @NotNull(message = "Order number cannot be null")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    @NotNull(message = "Total order value cannot be null")
    @PositiveOrZero(
            message = "Total order value cannot be negative"
    )
    private Double totalOrderValue;

    private LocalDate orderDate;

    private LocalDate requiredDate;

    private LocalDate shippedDate;

    private String dateRange;

    // DEFAULT CONSTRUCTOR

    public OrderValueDto() {
    }

    // PARAMETERIZED CONSTRUCTOR

    public OrderValueDto(
            Integer orderNumber,
            Double totalOrderValue
    ) {

        this.orderNumber = orderNumber;
        this.totalOrderValue = totalOrderValue;
    }

    // GETTER FOR ORDER NUMBER

    public Integer getOrderNumber() {
        return orderNumber;
    }

    // SETTER FOR ORDER NUMBER

    public void setOrderNumber(
            Integer orderNumber
    ) {
        this.orderNumber = orderNumber;
    }

    // GETTER FOR TOTAL ORDER VALUE

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }

    // SETTER FOR TOTAL ORDER VALUE

    public void setTotalOrderValue(
            Double totalOrderValue
    ) {
        this.totalOrderValue = totalOrderValue;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(LocalDate requiredDate) {
        this.requiredDate = requiredDate;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    // equals()

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof OrderValueDto)) {
            return false;
        }

        OrderValueDto that =
                (OrderValueDto) o;

        return Objects.equals(
                orderNumber,
                that.orderNumber
        ) &&
                Objects.equals(
                        totalOrderValue,
                        that.totalOrderValue
                ) &&
                Objects.equals(
                        orderDate,
                        that.orderDate
                ) &&
                Objects.equals(
                        requiredDate,
                        that.requiredDate
                ) &&
                Objects.equals(
                        shippedDate,
                        that.shippedDate
                ) &&
                Objects.equals(
                        dateRange,
                        that.dateRange
                );
    }

    // hashCode()

    @Override
    public int hashCode() {

        return Objects.hash(
                orderNumber,
                totalOrderValue,
                orderDate,
                requiredDate,
                shippedDate,
                dateRange
        );
    }
}
