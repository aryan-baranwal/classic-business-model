package com.classicmodels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

// DTO used for receiving status update in PATCH API request
// Maps to: PATCH /api/orders/{orderNumber}/status
// Only contains the status field as only status is being updated
// DB Schema: status varchar(15) NOT NULL
public class OrderStatusUpdateDTO {

    // Matches: status varchar(15) NOT NULL
    // NotBlank — status cannot be null or empty as per DB NOT NULL constraint
    // Size(max=15) — matches varchar(15) column definition in DB schema
    @NotBlank(message = "Status is required")
    @Size(max = 15, message = "Status must not exceed 15 characters")
    private String status;

    // Default constructor
    public OrderStatusUpdateDTO() {}

    // All args constructor
    public OrderStatusUpdateDTO(String status) {
        this.status = status;
    }

    // Getter
    public String getStatus() { return status; }

    // Setter
    public void setStatus(String status) { this.status = status; }

    // equals — compares status field to check if two status update DTOs are identical
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatusUpdateDTO)) return false;
        OrderStatusUpdateDTO that = (OrderStatusUpdateDTO) o;
        return Objects.equals(status, that.status);
    }

    // hashCode — based on status field consistent with equals
    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    // toString — useful for logging and debugging status update requests
    @Override
    public String toString() {
        return "OrderStatusUpdateDTO{" +
                "status='" + status + '\'' +
                '}';
    }
}