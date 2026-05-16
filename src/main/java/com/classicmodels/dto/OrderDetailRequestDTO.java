package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

// DTO used for receiving order detail data in POST and PUT API requests
// Maps to: POST /api/orders/{orderNumber}/items
//          PUT  /api/orders/{orderNumber}/items/{productCode}
// Validation rules match the DB schema constraints of the orderdetails table
// DB Schema: orderNumber int(11) NOT NULL, productCode varchar(15) NOT NULL,
//            quantityOrdered int(11) NOT NULL, priceEach decimal(10,2) NOT NULL,
//            orderLineNumber smallint(6) NOT NULL
public class OrderDetailRequestDTO {

    // Matches: productCode varchar(15) NOT NULL
    // NotBlank — productCode cannot be null or empty as per DB NOT NULL constraint
    // Size(max=15) — matches varchar(15) column definition in DB schema
    @NotBlank(message = "Product code is required")
    @Size(max = 15, message = "Product code must not exceed 15 characters")
    private String productCode;

    // Matches: quantityOrdered int(11) NOT NULL
    // NotNull — cannot be null as per DB NOT NULL constraint
    // Positive — quantity must be a positive number, cannot be zero or negative
    @NotNull(message = "Quantity ordered is required")
    @Positive(message = "Quantity ordered must be a positive number")
    private Integer quantityOrdered;

    // Matches: priceEach decimal(10,2) NOT NULL
    // NotNull — cannot be null as per DB NOT NULL constraint
    // Positive — price must be a positive value, cannot be zero or negative
    @NotNull(message = "Price per unit is required")
    @Positive(message = "Price per unit must be a positive value")
    private BigDecimal priceEach;

    // Matches: orderLineNumber smallint(6) NOT NULL
    // NotNull — cannot be null as per DB NOT NULL constraint
    // Positive — line number must be a positive number
    @NotNull(message = "Order line number is required")
    @Positive(message = "Order line number must be a positive number")
    private Short orderLineNumber;

    // Default constructor
    public OrderDetailRequestDTO() {}

    // All args constructor
    public OrderDetailRequestDTO(String productCode, Integer quantityOrdered,
                                 BigDecimal priceEach, Short orderLineNumber) {
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
        this.orderLineNumber = orderLineNumber;
    }

    // Getters
    public String getProductCode() { return productCode; }
    public Integer getQuantityOrdered() { return quantityOrdered; }
    public BigDecimal getPriceEach() { return priceEach; }
    public Short getOrderLineNumber() { return orderLineNumber; }

    // Setters
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public void setQuantityOrdered(Integer quantityOrdered) { this.quantityOrdered = quantityOrdered; }
    public void setPriceEach(BigDecimal priceEach) { this.priceEach = priceEach; }
    public void setOrderLineNumber(Short orderLineNumber) { this.orderLineNumber = orderLineNumber; }
}