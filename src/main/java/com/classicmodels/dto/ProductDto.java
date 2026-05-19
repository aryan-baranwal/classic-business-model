package com.classicmodels.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductDto {

    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotBlank(message = "Product name is required")
    private String productName;

    /*
     * IMPORTANT:
     * In the Classic Models MySQL database,
     * quantityInStock is defined as SMALLINT.
     *
     * Java type for SMALLINT = Short
     *
     * Changed from Integer to Short to match the database
     * and avoid Hibernate schema validation errors.
     */
    @NotNull(message = "Quantity in stock is required")
    @PositiveOrZero(message = "Quantity in stock must be zero or greater")
    private Short quantityInStock;

    @NotNull(message = "Buy price is required")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Buy price must be greater than zero"
    )
    private BigDecimal buyPrice;

    @NotBlank(message = "Product line is required")
    private String productLine;

    public ProductDto() {
    }

    public ProductDto(String productCode,
                      String productName,
                      Short quantityInStock,
                      BigDecimal buyPrice,
                      String productLine) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.productLine = productLine;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Short getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Short quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDto)) {
            return false;
        }

        ProductDto other = (ProductDto) o;

        return productCode != null &&
                productCode.equals(other.productCode);
    }

    @Override
    public int hashCode() {
        return productCode != null ? productCode.hashCode() : 0;
    }
}