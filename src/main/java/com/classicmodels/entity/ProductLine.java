package com.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

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

    @OneToMany(mappedBy = "productLine")
    private List<Product> products;

    public ProductLine() {
    }

    public ProductLine(String productLine,
                       String textDescription,
                       List<Product> products) {
        this.productLine = productLine;
        this.textDescription = textDescription;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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