package com.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @EmbeddedId
    private PaymentId id;

    @NotNull(message = "Payment date must not be null")
    @Column(name = "paymentDate", nullable = false)
    private LocalDate paymentDate;

    @NotNull(message = "Payment amount must not be null")
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @MapsId("customerNumber")
    @JoinColumn(name = "customerNumber")
    private Customer customer;

    // Default Constructor
    public Payment() {
    }

    // Parameterized Constructor
    public Payment(PaymentId id,
                   LocalDate paymentDate,
                   BigDecimal amount,
                   Customer customer) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.customer = customer;
    }

    // Getter for id
    public PaymentId getId() {
        return id;
    }

    // Setter for id
    public void setId(PaymentId id) {
        this.id = id;
    }

    // Getter for paymentDate
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    // Setter for paymentDate
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Getter for amount
    public BigDecimal getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Getter for customer
    public Customer getCustomer() {
        return customer;
    }

    // Setter for customer
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Payment payment = (Payment) obj;
        return id != null ? id.equals(payment.id) : payment.id == null;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}