package com.classicmodels.repository;

import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

// Repository interface for the orderdetails table
// Extends JpaRepository with composite key class OrderDetailId
// Primary Key type: OrderDetailId (orderNumber + productCode)
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    // JPQL — Retrieve all product line items for a specific order
    // Used by: GET /api/orders/{orderNumber}/items
    @Query("SELECT od FROM OrderDetail od WHERE od.order.orderNumber = :orderNumber")
    List<OrderDetail> findByOrderNumber(@Param("orderNumber") Integer orderNumber);
}