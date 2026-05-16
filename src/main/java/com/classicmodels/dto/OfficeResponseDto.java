package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class OfficeResponseDto {

    @NotBlank(message = "Office code cannot be empty")
    @Size(max = 10, message = "Office code cannot exceed 10 characters")
    private String officeCode;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "Phone number cannot be empty")
    private String phone;

    @NotBlank(message = "Country cannot be empty")
    private String country;

    @NotBlank(message = "Territory cannot be empty")
    private String territory;

    public OfficeResponseDto() {
    }

    public OfficeResponseDto(String officeCode,
                             String city,
                             String phone,
                             String country,
                             String territory) {

        this.officeCode = officeCode;
        this.city = city;
        this.phone = phone;
        this.country = country;
        this.territory = territory;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeResponseDto that)) return false;

        return Objects.equals(officeCode, that.officeCode)
                && Objects.equals(city, that.city)
                && Objects.equals(phone, that.phone)
                && Objects.equals(country, that.country)
                && Objects.equals(territory, that.territory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                officeCode,
                city,
                phone,
                country,
                territory
        );
    }
}