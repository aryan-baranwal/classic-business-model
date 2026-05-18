package com.classicmodels.controller;

import com.classicmodels.dto.OrderDetailRequestDTO;
import com.classicmodels.dto.OrderDetailResponseDTO;
import com.classicmodels.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderDetailController {

    private final OrderService orderService;

    // =========================================================================
    //  POST /api/orders/{orderNumber}/items — Add a product to an order
    //  Request body: OrderDetailRequestDTO
    //                { "productCode": "S18_1749",
    //                  "quantityOrdered": 30,
    //                  "priceEach": 136.00,
    //                  "orderLineNumber": 3 }
    //  Response: OrderDetailResponseDTO
    // =========================================================================
    @PostMapping("/{orderNumber}/items")
    public ResponseEntity<OrderDetailResponseDTO> addItemToOrder(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderDetailRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.addItem(orderNumber, dto));
    }

    // =========================================================================
    //  GET /api/orders/{orderNumber}/items — List all line items for an order
    //  Response: List<OrderDetailResponseDTO>
    // =========================================================================
    @GetMapping("/{orderNumber}/items")
    public ResponseEntity<List<OrderDetailResponseDTO>> getOrderItems(
            @PathVariable Integer orderNumber) {
        return ResponseEntity.ok(orderService.getItems(orderNumber));
    }

    // =========================================================================
    //  PUT /api/orders/{orderNumber}/items/{productCode} — Update a line item
    //  Request body: OrderDetailRequestDTO (send only fields you want to change)
    //                { "quantityOrdered": 45,
    //                  "priceEach": 140.00,
    //                  "orderLineNumber": 3 }
    //  Response: OrderDetailResponseDTO
    // =========================================================================
    @PutMapping("/{orderNumber}/items/{productCode}")
    public ResponseEntity<OrderDetailResponseDTO> updateOrderItem(
            @PathVariable Integer orderNumber,
            @PathVariable String productCode,
            @Valid @RequestBody OrderDetailRequestDTO dto) {
        return ResponseEntity.ok(orderService.updateItem(orderNumber, productCode, dto));
    }

    // =========================================================================
    //  DELETE /api/orders/{orderNumber}/items/{productCode} — Remove a line item
    //  No request body needed
    //  Response: confirmation message + deleted row details
    // =========================================================================
    @DeleteMapping("/{orderNumber}/items/{productCode}")
    public ResponseEntity<Map<String, Object>> removeOrderItem(
            @PathVariable Integer orderNumber,
            @PathVariable String productCode) {

        OrderDetailResponseDTO deletedRow = orderService.removeItem(orderNumber, productCode);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "This row was deleted on request");
        response.put("deletedRow", deletedRow);

        return ResponseEntity.ok(response);
    }
}