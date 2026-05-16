package com.classicmodels.dto;

import java.math.BigDecimal;

// DTO used for sending order detail data back to the client
// Maps to: GET  /api/orders/{orderNumber}/items
//          POST /api/orders/{orderNumber}/items
//          PUT  /api/orders/{orderNumber}/items/{productCode}
// No validation annotations needed — this is an outgoing response DTO
// Data is already validated and sourced from the database
public class OrderDetailResponseDTO {

    // Order number this line item belongs to
    // Matches: orderNumber int(11) NOT NULL
    private Integer orderNumber;

    // Product code of the item
    // Matches: productCode varchar(15) NOT NULL
    private String productCode;

    // Product name for display purposes
    // Derived from the products table via join
    private String productName;

    // Number of units ordered
    // Matches: quantityOrdered int(11) NOT NULL
    private Integer quantityOrdered;

    // Price per unit at time of ordering
    // Matches: priceEach decimal(10,2) NOT NULL
    private BigDecimal priceEach;

    // Total line value — quantityOrdered x priceEach
    // Calculated field — not stored in DB
    private BigDecimal lineTotal;

    // Sequence number of this line item within the order
    // Matches: orderLineNumber smallint(6) NOT NULL
    private Short orderLineNumber;

    // Default constructor
    public OrderDetailResponseDTO() {}

    // All args constructor
    public OrderDetailResponseDTO(Integer orderNumber, String productCode, String productName,
                                  Integer quantityOrdered, BigDecimal priceEach,
                                  BigDecimal lineTotal, Short orderLineNumber) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.productName = productName;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
        this.lineTotal = lineTotal;
        this.orderLineNumber = orderLineNumber;
    }

    // Getters
    public Integer getOrderNumber() { return orderNumber; }
    public String getProductCode() { return productCode; }
    public String getProductName() { return productName; }
    public Integer getQuantityOrdered() { return quantityOrdered; }
    public BigDecimal getPriceEach() { return priceEach; }
    public BigDecimal getLineTotal() { return lineTotal; }
    public Short getOrderLineNumber() { return orderLineNumber; }

    // Setters
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setQuantityOrdered(Integer quantityOrdered) { this.quantityOrdered = quantityOrdered; }
    public void setPriceEach(BigDecimal priceEach) { this.priceEach = priceEach; }
    public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
    public void setOrderLineNumber(Short orderLineNumber) { this.orderLineNumber = orderLineNumber; }
}