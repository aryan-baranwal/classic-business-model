package com.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "productlines")
public class ProductLine {

    @Id
    @Column(name = "productLine")
    @NotBlank(message = "Product line is required")
    private String productLine;

    @Column(name = "textDescription")
    @NotBlank(message = "Text description is required")
    private String textDescription;

    public ProductLine() {
    }

    public ProductLine(String productLine,
                       String textDescription) {
        this.productLine = productLine;
        this.textDescription = textDescription;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductLine)) {
            return false;
        }

        ProductLine other = (ProductLine) o;

        return productLine != null &&
                productLine.equals(other.productLine);
    }

    @Override
    public int hashCode() {
        return productLine != null ? productLine.hashCode() : 0;
    }
}