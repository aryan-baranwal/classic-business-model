package com.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "productCode")
    @NotBlank(message = "Product code is required")
    private String productCode;

    @Column(name = "productName")
    @NotBlank(message = "Product name is required")
    private String productName;

    /*
     * IMPORTANT FIX:
     * quantityInStock column in MySQL is SMALLINT.
     * Java type for SMALLINT is Short.
     *
     * Changed from Integer -> Short to fix:
     * Schema-validation: found [smallint] but expecting [integer]
     */
    @Column(name = "quantityInStock", columnDefinition = "SMALLINT")
    @NotNull(message = "Quantity in stock is required")
    @PositiveOrZero(message = "Quantity in stock must be zero or greater")
    private Short quantityInStock;

    @Column(name = "buyPrice")
    @NotNull(message = "Buy price is required")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Buy price must be greater than zero"
    )
    private BigDecimal buyPrice;

    @ManyToOne
    @JoinColumn(name = "productLine", referencedColumnName = "productLine")
    @NotNull(message = "Product line is required")
    private ProductLine productLine;

    public Product() {
    }

    public Product(String productCode,
                   String productName,
                   Short quantityInStock,
                   BigDecimal buyPrice,
                   ProductLine productLine) {
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

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }

        Product product = (Product) o;

        return productCode != null &&
                productCode.equals(product.productCode);
    }

    @Override
    public int hashCode() {
        return productCode != null ? productCode.hashCode() : 0;
    }
}