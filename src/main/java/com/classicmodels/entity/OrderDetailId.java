package com.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// Composite primary key class for the orderdetails table
// DB Schema: PRIMARY KEY (orderNumber, productCode)
// Must implement Serializable as required by JPA for composite keys
@Embeddable
public class OrderDetailId implements Serializable {

    // Part 1 of composite primary key
    // int(11) NOT NULL — references orders table
    @Column(name = "orderNumber")
    private Integer orderNumber;

    // Part 2 of composite primary key
    // varchar(15) NOT NULL — references products table
    @Column(name = "productCode")
    private String productCode;

    // Default constructor — required by JPA
    public OrderDetailId() {}

    // All args constructor
    public OrderDetailId(Integer orderNumber, String productCode) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
    }

    // Getters
    public Integer getOrderNumber() { return orderNumber; }
    public String getProductCode() { return productCode; }

    // Setters
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    // equals — MANDATORY for composite key classes
    // JPA uses this to compare and identify composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailId)) return false;
        OrderDetailId that = (OrderDetailId) o;
        return Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(productCode, that.productCode);
    }

    // hashCode — MANDATORY for composite key classes
    // JPA uses this to identify records in hash based collections
    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, productCode);
    }
}