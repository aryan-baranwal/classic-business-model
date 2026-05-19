/*
 * Customer Request DTO
 * Used to receive customer data from client requests.
 * Contains validation annotations and customer details.
 */

package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class CustomerRequestDTO {

    // Customer primary key
    private Integer customerNumber;

    @NotBlank(message = "Customer name must not be blank")
    private String customerName;

    @NotBlank(message = "Contact last name must not be blank")
    private String contactLastName;

    @NotBlank(message = "Contact first name must not be blank")
    private String contactFirstName;

    @NotBlank(message = "Phone number must not be blank")
    private String phone;

    @NotBlank(message = "Address line 1 must not be blank")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City must not be blank")
    private String city;

    private String state;

    private String postalCode;

    @NotBlank(message = "Country must not be blank")
    private String country;

    private BigDecimal creditLimit;

    // Default Constructor
    public CustomerRequestDTO() {
    }

    // Getter for customerNumber
    public Integer getCustomerNumber() {
        return customerNumber;
    }

    // Setter for customerNumber
    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    // Getter for customerName
    public String getCustomerName() {
        return customerName;
    }

    // Setter for customerName
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Getter for contactLastName
    public String getContactLastName() {
        return contactLastName;
    }

    // Setter for contactLastName
    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    // Getter for contactFirstName
    public String getContactFirstName() {
        return contactFirstName;
    }

    // Setter for contactFirstName
    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    // Getter for phone
    public String getPhone() {
        return phone;
    }

    // Setter for phone
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter for addressLine1
    public String getAddressLine1() {
        return addressLine1;
    }

    // Setter for addressLine1
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    // Getter for addressLine2
    public String getAddressLine2() {
        return addressLine2;
    }

    // Setter for addressLine2
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    // Getter for city
    public String getCity() {
        return city;
    }

    // Setter for city
    public void setCity(String city) {
        this.city = city;
    }

    // Getter for state
    public String getState() {
        return state;
    }

    // Setter for state
    public void setState(String state) {
        this.state = state;
    }

    // Getter for postalCode
    public String getPostalCode() {
        return postalCode;
    }

    // Setter for postalCode
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    // Getter for country
    public String getCountry() {
        return country;
    }

    // Setter for country
    public void setCountry(String country) {
        this.country = country;
    }

    // Getter for creditLimit
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    // Setter for creditLimit
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CustomerRequestDTO that = (CustomerRequestDTO) obj;

        if (customerName != null
                ? !customerName.equals(that.customerName)
                : that.customerName != null) {
            return false;
        }

        if (phone != null
                ? !phone.equals(that.phone)
                : that.phone != null) {
            return false;
        }

        return country != null
                ? country.equals(that.country)
                : that.country == null;
    }

    // hashCode method
    @Override
    public int hashCode() {

        int result = customerName != null
                ? customerName.hashCode()
                : 0;

        result = 31 * result
                + (phone != null ? phone.hashCode() : 0);

        result = 31 * result
                + (country != null ? country.hashCode() : 0);

        return result;
    }
}