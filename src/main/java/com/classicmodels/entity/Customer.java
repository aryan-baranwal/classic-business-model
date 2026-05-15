package com.classicmodels.entity;

import com.classicmodels.entity.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customerNumber")
    private Integer customerNumber;

    @NotBlank(message = "Customer name must not be blank")
    @Column(name = "customerName", nullable = false)
    private String customerName;

    @NotBlank(message = "Contact last name must not be blank")
    @Column(name = "contactLastName", nullable = false)
    private String contactLastName;

    @NotBlank(message = "Contact first name must not be blank")
    @Column(name = "contactFirstName", nullable = false)
    private String contactFirstName;

    @NotBlank(message = "Phone number must not be blank")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotBlank(message = "Address line 1 must not be blank")
    @Column(name = "addressLine1", nullable = false)
    private String addressLine1;

    @Column(name = "addressLine2")
    private String addressLine2;

    @NotBlank(message = "City must not be blank")
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postalCode")
    private String postalCode;

    @NotBlank(message = "Country must not be blank")
    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "creditLimit")
    private BigDecimal creditLimit;

    @ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employee salesRepEmployee;

    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

    // Default Constructor
    public Customer() {
    }

    // Parameterized Constructor
    public Customer(Integer customerNumber,
                    String customerName,
                    String contactLastName,
                    String contactFirstName,
                    String phone,
                    String addressLine1,
                    String addressLine2,
                    String city,
                    String state,
                    String postalCode,
                    String country,
                    BigDecimal creditLimit,
                    Employee salesRepEmployee,
                    List<Payment> payments) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.contactLastName = contactLastName;
        this.contactFirstName = contactFirstName;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.creditLimit = creditLimit;
        this.salesRepEmployee = salesRepEmployee;
        this.payments = payments;
    }

    public Integer getCustomerNumber() { return customerNumber; }
    public void setCustomerNumber(Integer customerNumber) { this.customerNumber = customerNumber; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getContactLastName() { return contactLastName; }
    public void setContactLastName(String contactLastName) { this.contactLastName = contactLastName; }

    public String getContactFirstName() { return contactFirstName; }
    public void setContactFirstName(String contactFirstName) { this.contactFirstName = contactFirstName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) { this.creditLimit = creditLimit; }

    public Employee getSalesRepEmployee() { return salesRepEmployee; }
    public void setSalesRepEmployee(Employee salesRepEmployee) { this.salesRepEmployee = salesRepEmployee; }

    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }
}