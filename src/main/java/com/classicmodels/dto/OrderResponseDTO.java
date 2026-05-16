package com.classicmodels.dto;

import java.time.LocalDate;

// DTO used for sending order data back to the client in API responses
// Maps to: GET /api/orders, GET /api/orders/{orderNumber}
//          GET /api/customers/{customerNumber}/orders
//          GET /api/orders/search?status=&fromDate=&toDate=
// No validation annotations needed — this is an outgoing response DTO
// Data is already validated and sourced from the database
public class OrderResponseDTO {

    // Unique identifier of the order
    // Matches: orderNumber int(11) NOT NULL
    private Integer orderNumber;

    // Date the order was placed
    // Matches: orderDate date NOT NULL
    private LocalDate orderDate;

    // Date by which the customer needs the order
    // Matches: requiredDate date NOT NULL
    private LocalDate requiredDate;

    // Date the order was shipped — null if not yet shipped
    // Matches: shippedDate date DEFAULT NULL
    private LocalDate shippedDate;

    // Current status of the order
    // Matches: status varchar(15) NOT NULL
    private String status;

    // Optional comments about the order
    // Matches: comments text DEFAULT NULL
    private String comments;

    // Customer number who placed the order
    // Matches: customerNumber int(11) NOT NULL
    private Integer customerNumber;

    // Customer name for display purposes
    // Derived from the customers table via join
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
}