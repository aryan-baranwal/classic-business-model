package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.*;

public class OfficeRequestDto {

    @NotBlank(message = "Office code is required")
    @Size(max = 10, message = "Office code cannot exceed 10 characters")
    @Pattern(
            regexp = "[1-9][0-9]*",
            message = "Office code must be a positive whole number without spaces"
    )
    private String officeCode;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Territory is required")
    private String territory;

    public OfficeRequestDto() {
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
        if (!(o instanceof OfficeRequestDto)) return false;
        OfficeRequestDto that = (OfficeRequestDto) o;
        return Objects.equals(officeCode, that.officeCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(officeCode);
    }
}
