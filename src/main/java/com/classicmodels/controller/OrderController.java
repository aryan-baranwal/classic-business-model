package com.classicmodels.controller;

import com.classicmodels.dto.OrderRequestDTO;
import com.classicmodels.dto.OrderResponseDTO;
import com.classicmodels.dto.OrderStatusUpdateDTO;
import com.classicmodels.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // =========================================================================
    //  POST /api/orders — Create a new order
    //  Request body: OrderRequestDTO (flat: orderNumber, orderDate,
    //                requiredDate, shippedDate, status, comments, customerNumber)
    //  Response: OrderResponseDTO
    // =========================================================================
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(dto));
    }

    // =========================================================================
    //  GET /api/orders — Retrieve all orders
    //  Response: List<OrderResponseDTO>
    // =========================================================================
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // =========================================================================
    //  GET /api/orders/{orderNumber} — Get order by ID
    //  Response: OrderResponseDTO
    // =========================================================================
    @GetMapping("/orders/{orderNumber}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Integer orderNumber) {
        return ResponseEntity.ok(orderService.getOrderById(orderNumber));
    }

    // =========================================================================
    //  PUT /api/orders/{orderNumber} — Update full order
    //  Request body: OrderRequestDTO
    //  Response: OrderResponseDTO
    // =========================================================================
    @PutMapping("/orders/{orderNumber}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.updateOrder(orderNumber, dto));
    }

    // =========================================================================
    //  PATCH /api/orders/{orderNumber}/status — Update order status only
    //  Request body: OrderStatusUpdateDTO  { "status": "Shipped" }
    //  Response: OrderResponseDTO
    // =========================================================================
    @PatchMapping("/orders/{orderNumber}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderStatusUpdateDTO dto) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderNumber, dto));
    }

    // =========================================================================
    //  GET /api/customers/{customerNumber}/orders — Orders by customer
    //  Response: List<OrderResponseDTO>
    // =========================================================================
    @GetMapping("/customers/{customerNumber}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomer(
            @PathVariable Integer customerNumber) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerNumber));
    }

    // =========================================================================
    //  GET /api/orders/search?status=&fromDate=&toDate= — Search orders
    //  All params optional
    //  Response: List<OrderResponseDTO>
    // =========================================================================
    @GetMapping("/orders/search")
    public ResponseEntity<List<OrderResponseDTO>> searchOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return ResponseEntity.ok(orderService.searchOrders(status, fromDate, toDate));
    }
}