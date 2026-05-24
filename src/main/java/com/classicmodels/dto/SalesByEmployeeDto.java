package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.Objects;

public class SalesByEmployeeDto {

    @NotBlank(message = "Employee name cannot be empty")
    private String employeeName;

    @NotNull(message = "Total sales cannot be null")
    @PositiveOrZero(message = "Total sales cannot be negative")
    private Double totalSales;

    private LocalDate reportStartDate;

    private LocalDate reportEndDate;

    private String dateRange;

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

    public LocalDate getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(LocalDate reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public LocalDate getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(LocalDate reportEndDate) {
        this.reportEndDate = reportEndDate;
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
                ) &&
                Objects.equals(
                        reportStartDate,
                        that.reportStartDate
                ) &&
                Objects.equals(
                        reportEndDate,
                        that.reportEndDate
                ) &&
                Objects.equals(
                        dateRange,
                        that.dateRange
                );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                employeeName,
                totalSales,
                reportStartDate,
                reportEndDate,
                dateRange
        );
    }
}
