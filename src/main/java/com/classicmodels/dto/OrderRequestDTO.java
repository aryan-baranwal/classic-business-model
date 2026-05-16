package com.classicmodels.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

// DTO used for receiving order data in POST and PUT API requests
// Maps to: POST /api/orders and PUT /api/orders/{orderNumber}
// Validation rules match the DB schema constraints of the orders table
// DB Schema: orderNumber int(11) NOT NULL, orderDate date NOT NULL,
//            requiredDate date NOT NULL, shippedDate date DEFAULT NULL,
//            status varchar(15) NOT NULL, comments text DEFAULT NULL,
//            customerNumber int(11) NOT NULL
public class OrderRequestDTO {

    // Matches: orderNumber int(11) NOT NULL
    // NOT NULL in DB — must be provided by client
    @NotNull(message = "Order number is required")
    private Integer orderNumber;

    // Matches: orderDate date NOT NULL
    // NOT NULL in DB — order date must always be provided
    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    // Matches: requiredDate date NOT NULL
    // NOT NULL in DB — required date must always be provided
    @NotNull(message = "Required date is required")
    private LocalDate requiredDate;

    // Matches: shippedDate date DEFAULT NULL
    // DEFAULT NULL in DB — optional, order may not be shipped yet
    private LocalDate shippedDate;

    // Matches: status varchar(15) NOT NULL
    // NOT NULL in DB — status must always be provided
    // Size(max=15) matches varchar(15) column definition
    @NotBlank(message = "Status is required")
    @Size(max = 15, message = "Status must not exceed 15 characters")
    private String status;

    // Matches: comments text DEFAULT NULL
    // DEFAULT NULL in DB — comments are optional
    private String comments;

    // Matches: customerNumber int(11) NOT NULL
    // NOT NULL in DB — every order must belong to a customer
    @NotNull(message = "Customer number is required")
    private Integer customerNumber;

    // Default constructor
    public OrderRequestDTO() {}

    // All args constructor
    public OrderRequestDTO(Integer orderNumber, LocalDate orderDate, LocalDate requiredDate,
                           LocalDate shippedDate, String status, String comments,
                           Integer customerNumber) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.customerNumber = customerNumber;
    }

    // Getters
    public Integer getOrderNumber() { return orderNumber; }
    public LocalDate getOrderDate() { return orderDate; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public LocalDate getShippedDate() { return shippedDate; }
    public String getStatus() { return status; }
    public String getComments() { return comments; }
    public Integer getCustomerNumber() { return customerNumber; }

    // Setters
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }
    public void setShippedDate(LocalDate shippedDate) { this.shippedDate = shippedDate; }
    public void setStatus(String status) { this.status = status; }
    public void setComments(String comments) { this.comments = comments; }
    public void setCustomerNumber(Integer customerNumber) { this.customerNumber = customerNumber; }
}