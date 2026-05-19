package com.classicmodels.service;

import com.classicmodels.dto.OrderDetailRequestDTO;
import com.classicmodels.dto.OrderDetailResponseDTO;
import com.classicmodels.dto.OrderRequestDTO;
import com.classicmodels.dto.OrderResponseDTO;
import com.classicmodels.dto.OrderStatusUpdateDTO;
import com.classicmodels.entity.OrderDetail;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    // ── POST /api/orders ───────────────────────────────────────────────────────
    OrderResponseDTO createOrder(OrderRequestDTO dto);

    // ── GET /api/orders ────────────────────────────────────────────────────────
    List<OrderResponseDTO> getAllOrders();

    // ── GET /api/orders/{orderNumber} ──────────────────────────────────────────
    OrderResponseDTO getOrderById(Integer orderNumber);

    // ── PUT /api/orders/{orderNumber} ──────────────────────────────────────────
    OrderResponseDTO updateOrder(Integer orderNumber, OrderRequestDTO dto);

    // ── PATCH /api/orders/{orderNumber}/status ─────────────────────────────────
    OrderResponseDTO updateOrderStatus(Integer orderNumber, OrderStatusUpdateDTO dto);

    // ── GET /api/customers/{customerNumber}/orders ─────────────────────────────
    List<OrderResponseDTO> getOrdersByCustomer(Integer customerNumber);

    // ── GET /api/orders/search?status=&fromDate=&toDate= ──────────────────────
    List<OrderResponseDTO> searchOrders(String status, LocalDate fromDate, LocalDate toDate);

    // ── POST /api/orders/{orderNumber}/items ───────────────────────────────────
    OrderDetailResponseDTO addItem(Integer orderNumber, OrderDetailRequestDTO dto);

    // ── GET /api/orders/{orderNumber}/items ────────────────────────────────────
    List<OrderDetailResponseDTO> getItems(Integer orderNumber);

    // ── PUT /api/orders/{orderNumber}/items/{productCode} ─────────────────────
    OrderDetailResponseDTO updateItem(Integer orderNumber, String productCode,
                                      OrderDetailRequestDTO dto);

    // ── DELETE /api/orders/{orderNumber}/items/{productCode} ──────────────────
    // Returns the deleted row so the controller can include it in the response body
    OrderDetailResponseDTO removeItem(Integer orderNumber, String productCode);


}