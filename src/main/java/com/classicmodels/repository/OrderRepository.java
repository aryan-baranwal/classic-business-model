package com.classicmodels.repository;

import com.classicmodels.entity.Order;
import com.classicmodels.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomer_CustomerNumber(Integer customerNumber);

    @Query("""
            SELECT o FROM Order o
            WHERE (:status   IS NULL OR o.status    = :status)
              AND (:fromDate IS NULL OR o.orderDate >= :fromDate)
              AND (:toDate   IS NULL OR o.orderDate <= :toDate)
            ORDER BY o.orderDate DESC
            """)
    List<Order> searchOrders(
            @Param("status")   String status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate
    );

    boolean existsByOrderNumber(Integer orderNumber);

    List<Order> findByStatus(String status);
}