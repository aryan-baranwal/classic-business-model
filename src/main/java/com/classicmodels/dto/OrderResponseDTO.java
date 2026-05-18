package com.classicmodels.dto;

import java.time.LocalDate;
import java.util.Objects;

// DTO used for sending order data back to the client in API responses
// Maps to: GET /api/orders, GET /api/orders/{orderNumber}
//          GET /api/customers/{customerNumber}/orders
//          GET /api/orders/search?status=&fromDate=&toDate=
// No validation annotations — outgoing response DTO
public class OrderResponseDTO {

    // Matches: orderNumber int(11) NOT NULL
    private Integer orderNumber;

    // Matches: orderDate date NOT NULL
    private LocalDate orderDate;

    // Matches: requiredDate date NOT NULL
    private LocalDate requiredDate;

    // Matches: shippedDate date DEFAULT NULL
    private LocalDate shippedDate;

    // Matches: status varchar(15) NOT NULL
    private String status;

    // Matches: comments text DEFAULT NULL
    private String comments;

    // Matches: customerNumber int(11) NOT NULL
    private Integer customerNumber;

    // Derived from customers table — for display purposes
    private String customerName;

    // Default constructor
    public OrderResponseDTO() {}

    // All args constructor
    public OrderResponseDTO(Integer orderNumber, LocalDate orderDate, LocalDate requiredDate,
                            LocalDate shippedDate, String status, String comments,
                            Integer customerNumber, String customerName) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
    }

    // Getters
    public Integer getOrderNumber() { return orderNumber; }
    public LocalDate getOrderDate() { return orderDate; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public LocalDate getShippedDate() { return shippedDate; }
    public String getStatus() { return status; }
    public String getComments() { return comments; }
    public Integer getCustomerNumber() { return customerNumber; }
    public String getCustomerName() { return customerName; }

    // Setters
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }
    public void setShippedDate(LocalDate shippedDate) { this.shippedDate = shippedDate; }
    public void setStatus(String status) { this.status = status; }
    public void setComments(String comments) { this.comments = comments; }
    public void setCustomerNumber(Integer customerNumber) { this.customerNumber = customerNumber; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    // equals — compares all fields to check if two response DTOs carry identical data
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderResponseDTO)) return false;
        OrderResponseDTO that = (OrderResponseDTO) o;
        return Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(orderDate, that.orderDate) &&
                Objects.equals(requiredDate, that.requiredDate) &&
                Objects.equals(shippedDate, that.shippedDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(comments, that.comments) &&
                Objects.equals(customerNumber, that.customerNumber) &&
                Objects.equals(customerName, that.customerName);
    }

    // hashCode — based on all fields consistent with equals
    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, orderDate, requiredDate,
                shippedDate, status, comments,
                customerNumber, customerName);
    }

    // toString — useful for logging and debugging response data
    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "orderNumber=" + orderNumber +
                ", orderDate=" + orderDate +
                ", requiredDate=" + requiredDate +
                ", shippedDate=" + shippedDate +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                ", customerNumber=" + customerNumber +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}