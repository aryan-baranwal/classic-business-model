package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;

public class MonthlyRevenueDto {

    @NotBlank(message = "Month cannot be empty")
    private String month;

    @NotNull(message = "Total revenue cannot be null")
    @PositiveOrZero(message = "Total revenue cannot be negative")
    private BigDecimal totalRevenue;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthlyRevenueDto)) return false;

        MonthlyRevenueDto that = (MonthlyRevenueDto) o;

        return Objects.equals(month, that.month)
                && Objects.equals(totalRevenue, that.totalRevenue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, totalRevenue);
    }
}