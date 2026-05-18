package com.classicmodels.dto;

import java.math.BigDecimal;
import java.util.Objects;

// DTO used for sending order detail data back to the client
// Maps to: GET  /api/orders/{orderNumber}/items
//          POST /api/orders/{orderNumber}/items
//          PUT  /api/orders/{orderNumber}/items/{productCode}
// No validation annotations — outgoing response DTO
public class OrderDetailResponseDTO {

    // Matches: orderNumber int(11) NOT NULL
    private Integer orderNumber;

    // Matches: productCode varchar(15) NOT NULL
    private String productCode;

    // Derived from products table — for display purposes
    private String productName;

    // Matches: quantityOrdered int(11) NOT NULL
    private Integer quantityOrdered;

    // Matches: priceEach decimal(10,2) NOT NULL
    private BigDecimal priceEach;

    // Calculated field — quantityOrdered x priceEach
    // Not stored in DB — computed in service layer
    private BigDecimal lineTotal;

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

    // equals — compares all fields to check if two response DTOs carry identical data
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailResponseDTO)) return false;
        OrderDetailResponseDTO that = (OrderDetailResponseDTO) o;
        return Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(productCode, that.productCode) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(quantityOrdered, that.quantityOrdered) &&
                Objects.equals(priceEach, that.priceEach) &&
                Objects.equals(lineTotal, that.lineTotal) &&
                Objects.equals(orderLineNumber, that.orderLineNumber);
    }

    // hashCode — based on all fields consistent with equals
    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, productCode, productName,
                quantityOrdered, priceEach, lineTotal, orderLineNumber);
    }

    // toString — useful for logging and debugging response data
    @Override
    public String toString() {
        return "OrderDetailResponseDTO{" +
                "orderNumber=" + orderNumber +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", quantityOrdered=" + quantityOrdered +
                ", priceEach=" + priceEach +
                ", lineTotal=" + lineTotal +
                ", orderLineNumber=" + orderLineNumber +
                '}';
    }
}