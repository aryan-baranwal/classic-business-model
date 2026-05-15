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

    public PaymentId getId() { return id; }
    public void setId(PaymentId id) { this.id = id; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}