

package com.classicmodels.entity;

import com.classicmodels.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer implements Persistable<Integer> {

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employee salesRepEmployee;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

    /*
     * This field is not stored in database.
     * Used by Spring Data JPA to identify whether
     * the entity is new or existing.
     */
    @Transient
    @JsonIgnore
    private boolean isNew = false;

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

    /*
     * Returns the primary key value.
     * Required by Persistable interface.
     */
    @Override
    @JsonIgnore
    public Integer getId() {
        return customerNumber;
    }

    /*
     * Tells Spring Data JPA whether this entity is new.
     * If true -> persist()
     * If false -> merge()
     */
    @Override
    @JsonIgnore
    public boolean isNew() {
        return isNew;
    }

    /*
     * Marks entity as new before saving.
     */
    public Customer markAsNew() {
        this.isNew = true;
        return this;
    }

    /*
     * After loading or persisting,
     * entity becomes existing entity.
     */
    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
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

    // Getter for salesRepEmployee
    public Employee getSalesRepEmployee() {
        return salesRepEmployee;
    }

    // Setter for salesRepEmployee
    public void setSalesRepEmployee(Employee salesRepEmployee) {
        this.salesRepEmployee = salesRepEmployee;
    }

    // Getter for payments
    public List<Payment> getPayments() {
        return payments;
    }

    // Setter for payments
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Customer customer = (Customer) obj;

        return customerNumber != null
                ? customerNumber.equals(customer.customerNumber)
                : customer.customerNumber == null;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return customerNumber != null ? customerNumber.hashCode() : 0;
    }
}