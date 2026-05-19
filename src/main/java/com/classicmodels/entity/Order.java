package com.classicmodels.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "orderNumber")
    private Integer orderNumber;

    @Column(name = "orderDate", nullable = false)
    private LocalDate orderDate;

    @Column(name = "requiredDate", nullable = false)
    private LocalDate requiredDate;

    @Column(name = "shippedDate")
    private LocalDate shippedDate;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Column(name = "comments", columnDefinition = "text")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "customerNumber", nullable = false)
    private Customer customer;

    // ✅ FIXED: initialize list + cascade
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order() {}

    // Getters
    public Integer getOrderNumber() { return orderNumber; }
    public LocalDate getOrderDate() { return orderDate; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public LocalDate getShippedDate() { return shippedDate; }
    public String getStatus() { return status; }
    public String getComments() { return comments; }
    public Customer getCustomer() { return customer; }
    public List<OrderDetail> getOrderDetails() { return orderDetails; }

    // Setters
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }
    public void setShippedDate(LocalDate shippedDate) { this.shippedDate = shippedDate; }
    public void setStatus(String status) { this.status = status; }
    public void setComments(String comments) { this.comments = comments; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }
}