package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductLineDto {

    @NotBlank(message = "Product line is required")
    private String productLine;

    @NotBlank(message = "Text description is required")
    private String textDescription;

    public ProductLineDto() {
    }

    public ProductLineDto(String productLine,
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
        if (!(o instanceof ProductLineDto)) {
            return false;
        }

        ProductLineDto other = (ProductLineDto) o;

        return productLine != null &&
                productLine.equals(other.productLine);
    }

    @Override
    public int hashCode() {
        return productLine != null ? productLine.hashCode() : 0;
    }
}