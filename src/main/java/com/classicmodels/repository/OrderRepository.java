package com.classicmodels.repository;

import com.classicmodels.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

// Repository interface for the orders table
// Extends JpaRepository to provide standard CRUD operations
// Primary Key type: Integer (orderNumber)
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // JPQL — Retrieve all orders placed by a specific customer
    // Used by: GET /api/customers/{customerNumber}/orders
    @Query("SELECT o FROM Order o WHERE o.customer.customerNumber = :customerNumber")
    List<Order> findByCustomerNumber(@Param("customerNumber") Integer customerNumber);

    // JPQL — Search orders by status and/or date range
    // All parameters are optional — null values are ignored in the filter
    // Used by: GET /api/orders/search?status=&fromDate=&toDate=
    @Query("SELECT o FROM Order o WHERE " +
            "(:status IS NULL OR o.status = :status) AND " +
            "(:fromDate IS NULL OR o.orderDate >= :fromDate) AND " +
            "(:toDate IS NULL OR o.orderDate <= :toDate)")
    List<Order> searchOrders(@Param("status") String status,
                                   @Param("fromDate") LocalDate fromDate,
                                   @Param("toDate") LocalDate toDate);
}