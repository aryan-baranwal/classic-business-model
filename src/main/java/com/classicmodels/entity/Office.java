package com.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "offices")
public class Office {

    @Id
    @Column(name = "officeCode")
    @NotBlank(message = "Office code cannot be empty")
    @Size(max = 10, message = "Office code cannot exceed 10 characters")
    private String officeCode;

    @Column(name = "city")
    @NotBlank(message = "City cannot be empty")
    private String city;

    @Column(name = "phone")
    @NotBlank(message = "Phone number cannot be empty")
    private String phone;

    @Column(name = "addressLine1")
    @NotBlank(message = "Address Line 1 cannot be empty")
    private String addressLine1;

    @Column(name = "addressLine2")
    private String addressLine2;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    @NotBlank(message = "Country cannot be empty")
    private String country;

    @Column(name = "postalCode")
    @NotBlank(message = "Postal code cannot be empty")
    private String postalCode;

    @Column(name = "territory")
    @NotBlank(message = "Territory cannot be empty")
    private String territory;

    public Office() {
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
        if (!(o instanceof Office office)) return false;

        return Objects.equals(officeCode, office.officeCode)
                && Objects.equals(city, office.city)
                && Objects.equals(phone, office.phone)
                && Objects.equals(addressLine1, office.addressLine1)
                && Objects.equals(addressLine2, office.addressLine2)
                && Objects.equals(state, office.state)
                && Objects.equals(country, office.country)
                && Objects.equals(postalCode, office.postalCode)
                && Objects.equals(territory, office.territory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                officeCode,
                city,
                phone,
                addressLine1,
                addressLine2,
                state,
                country,
                postalCode,
                territory
        );
    }
}