package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class CustomerExposureDto {

    @NotBlank(message = "Customer name cannot be empty")
    private String customerName;

    @NotNull(message = "Credit limit cannot be null")
    @PositiveOrZero(message = "Credit limit cannot be negative")
    private Double creditLimit;

    @NotNull(message = "Total orders value cannot be null")
    @PositiveOrZero(message = "Total orders value cannot be negative")
    private Double totalOrders;

    @NotNull(message = "Exposure cannot be null")
    private Double exposure;

    public CustomerExposureDto() {
    }

    public CustomerExposureDto(
            String customerName,
            Double creditLimit,
            Double totalOrders,
            Double exposure
    ) {
        this.customerName = customerName;
        this.creditLimit = creditLimit;
        this.totalOrders = totalOrders;
        this.exposure = exposure;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Double totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CustomerExposureDto)) {
            return false;
        }

        CustomerExposureDto that =
                (CustomerExposureDto) o;

        return Objects.equals(
                customerName,
                that.customerName
        ) &&
                Objects.equals(
                        creditLimit,
                        that.creditLimit
                ) &&
                Objects.equals(
                        totalOrders,
                        that.totalOrders
                ) &&
                Objects.equals(
                        exposure,
                        that.exposure
                );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                customerName,
                creditLimit,
                totalOrders,
                exposure
        );
    }
}