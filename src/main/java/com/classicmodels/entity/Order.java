package com.classicmodels.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "orderNumber")
    @NotNull(message = "Order number cannot be null")
    private Integer orderNumber;

    @Column(name = "orderDate", nullable = false)
    @NotNull(message = "Order date cannot be null")
    private LocalDate orderDate;

    @Column(name = "requiredDate", nullable = false)
    @NotNull(message = "Required date cannot be null")
    private LocalDate requiredDate;

    @Column(name = "shippedDate")
    private LocalDate shippedDate;

    @Column(name = "status", nullable = false, length = 15)
    @NotBlank(message = "Order status cannot be empty")
    private String status;

    @Column(name = "comments", columnDefinition = "text")
    private String comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerNumber", nullable = false)
    @JsonIgnoreProperties({
            "orders",
            "payments",
            "salesRepEmployee"
    })
    @NotNull(message = "Customer cannot be null")
    private Customer customer;

    // REQUIRED FOR REPORT ENDPOINTS

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<OrderDetail> orderDetails =
            new ArrayList<>();

    // Default Constructor

    public Order() {
    }

    // Getters

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getRequiredDate() {
        return requiredDate;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public String getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    // Setters

    public void setOrderNumber(
            Integer orderNumber
    ) {
        this.orderNumber = orderNumber;
    }

    public void setOrderDate(
            LocalDate orderDate
    ) {
        this.orderDate = orderDate;
    }

    public void setRequiredDate(
            LocalDate requiredDate
    ) {
        this.requiredDate = requiredDate;
    }

    public void setShippedDate(
            LocalDate shippedDate
    ) {
        this.shippedDate = shippedDate;
    }

    public void setStatus(
            String status
    ) {
        this.status = status;
    }

    public void setComments(
            String comments
    ) {
        this.comments = comments;
    }

    public void setCustomer(
            Customer customer
    ) {
        this.customer = customer;
    }

    public void setOrderDetails(
            List<OrderDetail> orderDetails
    ) {
        this.orderDetails = orderDetails;
    }

    // equals

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Order)) {
            return false;
        }

        Order order = (Order) o;

        return Objects.equals(
                orderNumber,
                order.orderNumber
        );
    }

    // hashCode

    @Override
    public int hashCode() {

        return Objects.hash(
                orderNumber
        );
    }
}