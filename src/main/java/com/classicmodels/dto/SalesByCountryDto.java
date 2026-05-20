package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class SalesByCountryDto {

    @NotBlank(message = "Country name cannot be empty")
    private String country;

    @NotNull(message = "Total sales cannot be null")
    @PositiveOrZero(message = "Total sales cannot be negative")
    private Double totalSales;

    public SalesByCountryDto() {
    }

    public SalesByCountryDto(
            String country,
            Double totalSales
    ) {
        this.country = country;
        this.totalSales = totalSales;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

        if (!(o instanceof SalesByCountryDto)) {
            return false;
        }

        SalesByCountryDto that =
                (SalesByCountryDto) o;

        return Objects.equals(
                country,
                that.country
        ) &&
                Objects.equals(
                        totalSales,
                        that.totalSales
                );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                country,
                totalSales
        );
    }
}