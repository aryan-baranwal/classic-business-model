package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class MonthlyRevenueDto {

    @NotBlank(message = "Month cannot be empty")
    private String month;

    @NotNull(message = "Total revenue cannot be null")
    @PositiveOrZero(message = "Total revenue cannot be negative")
    private BigDecimal totalRevenue;

    private LocalDate monthStartDate;

    private LocalDate monthEndDate;

    private String dateRange;

    // Default constructor
    public MonthlyRevenueDto() {
    }

    // Required by JPQL constructor expression
    public MonthlyRevenueDto(
            String month,
            BigDecimal totalRevenue
    ) {
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public LocalDate getMonthStartDate() {
        return monthStartDate;
    }

    public void setMonthStartDate(LocalDate monthStartDate) {
        this.monthStartDate = monthStartDate;
    }

    public LocalDate getMonthEndDate() {
        return monthEndDate;
    }

    public void setMonthEndDate(LocalDate monthEndDate) {
        this.monthEndDate = monthEndDate;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthlyRevenueDto)) return false;

        MonthlyRevenueDto that = (MonthlyRevenueDto) o;

        return Objects.equals(month, that.month)
                && Objects.equals(totalRevenue, that.totalRevenue)
                && Objects.equals(monthStartDate, that.monthStartDate)
                && Objects.equals(monthEndDate, that.monthEndDate)
                && Objects.equals(dateRange, that.dateRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, totalRevenue, monthStartDate, monthEndDate, dateRange);
    }
}
