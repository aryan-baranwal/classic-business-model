package com.classicmodels.repository;

import com.classicmodels.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Integer> {

    // FIND ORDERS BY CUSTOMER NUMBER

    List<Order> findByCustomer_CustomerNumber(
            Integer customerNumber
    );

    // SEARCH ORDERS WITH FILTERS

    @Query("""
            SELECT o FROM Order o
            WHERE (:status IS NULL
                   OR o.status = :status)

            AND (:fromDate IS NULL
                 OR o.orderDate >= :fromDate)

            AND (:toDate IS NULL
                 OR o.orderDate <= :toDate)

            ORDER BY o.orderDate DESC
            """)
    List<Order> searchOrders(

            @Param("status")
            String status,

            @Param("fromDate")
            LocalDate fromDate,

            @Param("toDate")
            LocalDate toDate
    );

    // CHECK ORDER EXISTS

    boolean existsByOrderNumber(
            Integer orderNumber
    );

    // FIND ORDERS BY STATUS

    List<Order> findByStatus(
            String status
    );

    // ORDER VALUE REPORT

    @Query("""
       SELECT
       o.orderNumber,
       SUM(od.quantityOrdered * od.priceEach),
       o.orderDate,
       o.requiredDate,
       o.shippedDate

       FROM Order o

       JOIN o.orderDetails od

       WHERE o.orderNumber = :orderNumber

       GROUP BY o.orderNumber,
                o.orderDate,
                o.requiredDate,
                o.shippedDate
       """)
    List<Object[]> getOrderValue(

            @Param("orderNumber")
            Integer orderNumber
    );
}
