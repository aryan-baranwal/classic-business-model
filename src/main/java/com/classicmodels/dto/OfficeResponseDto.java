package com.classicmodels.dto;

public class OfficeResponseDto {

    private String officeCode;
    private String city;
    private String phone;
    private String country;
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
}