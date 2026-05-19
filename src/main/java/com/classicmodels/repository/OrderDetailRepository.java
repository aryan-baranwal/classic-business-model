package com.classicmodels.repository;

import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    // ── GET /api/orders/{orderNumber}/items ────────────────────────────────────
    // Returns all line items belonging to the given order number
    List<OrderDetail> findById_OrderNumber(Integer orderNumber);

    // ── Existence check before update / delete ─────────────────────────────────
    boolean existsById_OrderNumberAndId_ProductCode(Integer orderNumber, String productCode);

    // ── DELETE /api/orders/{orderNumber}/items/{productCode} ───────────────────
    @Modifying
    @Query("""
            DELETE FROM OrderDetail od
            WHERE od.id.orderNumber = :orderNumber
              AND od.id.productCode = :productCode
            """)
    void deleteByOrderNumberAndProductCode(
            @Param("orderNumber") Integer orderNumber,
            @Param("productCode") String productCode
    );

    // ── GET /api/reports/order-value/{orderNumber} ─────────────────────────────
    // Calculates total order value: SUM(quantityOrdered * priceEach)
    // Returns 0 when the order has no line items
    @Query("""
            SELECT COALESCE(SUM(od.quantityOrdered * od.priceEach), 0)
            FROM OrderDetail od
            WHERE od.id.orderNumber = :orderNumber
            """)
    BigDecimal calculateOrderTotal(@Param("orderNumber") Integer orderNumber);
}