package com.classicmodels.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "quantityOrdered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "priceEach", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceEach;

    @Column(name = "orderLineNumber", nullable = false)
    private Short orderLineNumber;

   
    @ManyToOne
    @MapsId("orderNumber")
    @JoinColumn(name = "orderNumber")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name = "productCode")
    private Product product;

    public OrderDetail() {}

    // Getters
    public OrderDetailId getId() { return id; }
    public Integer getQuantityOrdered() { return quantityOrdered; }
    public BigDecimal getPriceEach() { return priceEach; }
    public Short getOrderLineNumber() { return orderLineNumber; }
    public Order getOrder() { return order; }
    public Product getProduct() { return product; }

    // Setters
    public void setId(OrderDetailId id) { this.id = id; }
    public void setQuantityOrdered(Integer quantityOrdered) { this.quantityOrdered = quantityOrdered; }
    public void setPriceEach(BigDecimal priceEach) { this.priceEach = priceEach; }
    public void setOrderLineNumber(Short orderLineNumber) { this.orderLineNumber = orderLineNumber; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public void setOrder(Order order) {
        this.order = order;
        if (this.id == null) this.id = new OrderDetailId();
        this.id.setOrderNumber(order.getOrderNumber());
    }

    public void setProduct(Product product) {
        this.product = product;
        if (this.id == null) this.id = new OrderDetailId();
        this.id.setProductCode(product.getProductCode());
    }
}
