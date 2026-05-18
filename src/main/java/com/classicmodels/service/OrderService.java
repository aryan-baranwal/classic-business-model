package com.classicmodels.service;

import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    // ── Order CRUD ──────────────────────────────────────────────────────────

    /** POST /api/orders – Create a new order */
    Order createOrder(Order order);

    /** GET /api/orders – Retrieve all orders */
    List<Order> getAllOrders();

    /** GET /api/orders/{orderNumber} – Get order details */
    Order getOrderById(Integer orderNumber);

    /** PUT /api/orders/{orderNumber} – Update order (dates, comments) */
    Order updateOrder(Integer orderNumber, Order updatedOrder);

    /** PATCH /api/orders/{orderNumber}/status – Update order status */
    Order updateOrderStatus(Integer orderNumber, String status);

    /** GET /api/customers/{customerNumber}/orders – Orders for a customer */
    List<Order> getOrdersByCustomer(Integer customerNumber);

    /**
     * GET /api/orders/search?status=&fromDate=&toDate=
     * Search orders by status and optional date range
     */
    List<Order> searchOrders(String status, LocalDate fromDate, LocalDate toDate);

    // ── Order Line Items ─────────────────────────────────────────────────────

    /** POST /api/orders/{orderNumber}/items – Add product to an order */
    OrderDetail addItemToOrder(Integer orderNumber, OrderDetail item);

    /** GET /api/orders/{orderNumber}/items – List order line items */
    List<OrderDetail> getOrderItems(Integer orderNumber);

    /** PUT /api/orders/{orderNumber}/items/{productCode} – Update qty / price */
    OrderDetail updateOrderItem(Integer orderNumber, String productCode, OrderDetail updatedItem);

    /** DELETE /api/orders/{orderNumber}/items/{productCode} – Remove item */
    void removeOrderItem(Integer orderNumber, String productCode);

    // ── Reporting ────────────────────────────────────────────────────────────

    /** GET /api/reports/order-value/{orderNumber} – Total order value */
    BigDecimal calculateOrderValue(Integer orderNumber);
}