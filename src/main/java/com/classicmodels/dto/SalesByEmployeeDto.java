package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class SalesByEmployeeDto {

    @NotBlank(message = "Employee name cannot be empty")
    private String employeeName;

    @NotNull(message = "Total sales cannot be null")
    @PositiveOrZero(message = "Total sales cannot be negative")
    private Double totalSales;

    public SalesByEmployeeDto() {
    }

    public SalesByEmployeeDto(
            String employeeName,
            Double totalSales
    ) {
        this.employeeName = employeeName;
        this.totalSales = totalSales;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof SalesByEmployeeDto)) {
            return false;
        }

        SalesByEmployeeDto that =
                (SalesByEmployeeDto) o;

        return Objects.equals(
                employeeName,
                that.employeeName
        ) &&
                Objects.equals(
                        totalSales,
                        that.totalSales
                );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                employeeName,
                totalSales
        );
    }
}