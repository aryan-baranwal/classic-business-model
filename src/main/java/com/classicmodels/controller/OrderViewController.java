package com.classicmodels.controller;

import com.classicmodels.dto.OrderDetailRequestDTO;
import com.classicmodels.dto.OrderDetailResponseDTO;
import com.classicmodels.dto.OrderRequestDTO;
import com.classicmodels.dto.OrderResponseDTO;
import com.classicmodels.dto.OrderStatusUpdateDTO;
import com.classicmodels.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderViewController {

    private final OrderService orderService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "order/dashboard";
    }

    // =========================================================================
    // GET /orders — Show all orders as table
    // =========================================================================
    @GetMapping
    public String getAllOrders(Model model) {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    // =========================================================================
    // GET /orders/{orderNumber} — Show single order details
    // =========================================================================
    @GetMapping("/{orderNumber}")
    public String getOrderById(
            @PathVariable
            @Positive(message = "Order number must be positive")
            Integer orderNumber,
            Model model) {
        OrderResponseDTO order = orderService.getOrderById(orderNumber);
        model.addAttribute("order", order);
        return "order/details";
    }

    // =========================================================================
    // GET /orders/new — Show create order form
    // =========================================================================
    @GetMapping("/new")
    public String showCreateOrderForm(Model model) {
        model.addAttribute("orderDTO", new OrderRequestDTO());
        return "order/create";
    }

    // =========================================================================
    // POST /orders/new — Submit create order form
    // =========================================================================
    @PostMapping("/new")
    public String createOrder(@Valid @ModelAttribute("orderDTO") OrderRequestDTO dto) {
        OrderResponseDTO created = orderService.createOrder(dto);
        return "redirect:/orders/" + created.getOrderNumber();
    }

    // =========================================================================
    // GET /orders/{orderNumber}/edit — Show edit order form
    // =========================================================================
    @GetMapping("/{orderNumber}/edit")
    public String showEditOrderForm(
            @PathVariable
            @Positive(message = "Order number must be positive")
            Integer orderNumber,
            Model model) {
        OrderResponseDTO order = orderService.getOrderById(orderNumber);
        // Populate the request DTO from existing data
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setOrderNumber(order.getOrderNumber());
        dto.setOrderDate(order.getOrderDate());
        dto.setRequiredDate(order.getRequiredDate());
        dto.setShippedDate(order.getShippedDate());
        dto.setStatus(order.getStatus());
        dto.setComments(order.getComments());
        model.addAttribute("orderDTO", dto);
        model.addAttribute("orderNumber", orderNumber);
        return "order/edit";
    }

    // =========================================================================
    // POST /orders/{orderNumber}/edit — Submit edit order form (simulates PUT)
    // =========================================================================
    @PostMapping("/{orderNumber}/edit")
    public String updateOrder(
                              @PathVariable
                              @Positive(message = "Order number must be positive")
                              Integer orderNumber,
                              @Valid @ModelAttribute("orderDTO") OrderRequestDTO dto) {
        orderService.updateOrder(orderNumber, dto);
        return "redirect:/orders/" + orderNumber;
    }

    // =========================================================================
    // GET /orders/{orderNumber}/status — Show update status form
    // =========================================================================
    @GetMapping("/{orderNumber}/status")
    public String showStatusForm(
            @PathVariable
            @Positive(message = "Order number must be positive")
            Integer orderNumber,
            Model model) {
        OrderResponseDTO order = orderService.getOrderById(orderNumber);
        model.addAttribute("order", order);
        model.addAttribute("statusDTO", new OrderStatusUpdateDTO());
        return "order/status";
    }

    // =========================================================================
    // POST /orders/{orderNumber}/status — Submit status update (simulates PATCH)
    // =========================================================================
    @PostMapping("/{orderNumber}/status")
    public String updateOrderStatus(
                                    @PathVariable
                                    @Positive(message = "Order number must be positive")
                                    Integer orderNumber,
                                    @Valid @ModelAttribute("statusDTO") OrderStatusUpdateDTO dto) {
        orderService.updateOrderStatus(orderNumber, dto);
        return "redirect:/orders/" + orderNumber;
    }

    // =========================================================================
    // GET /orders/search — Show search form and results
    // =========================================================================
    @GetMapping("/search")
    public String searchOrders(
            @RequestParam(required = false)
            @Size(max = 15, message = "Status must not exceed 15 characters")
            @Pattern(
                    regexp = "\\S(.*\\S)?",
                    message = "Status must not be blank or start/end with spaces"
            )
            String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Model model) {
        List<OrderResponseDTO> orders = orderService.searchOrders(status, fromDate, toDate);
        model.addAttribute("orders", orders);
        model.addAttribute("showSearchForm", true);
        model.addAttribute("pageTitle", "Search Orders");
        return "order/orders";
    }

    // =========================================================================
    // GET /orders/customers/{customerNumber}/orders — Orders by customer
    // =========================================================================
    @GetMapping("/customers/{customerNumber}/orders")
    public String getOrdersByCustomer(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            Model model) {
        List<OrderResponseDTO> orders = orderService.getOrdersByCustomer(customerNumber);
        model.addAttribute("orders", orders);
        model.addAttribute("showSearchForm", false);
        model.addAttribute("pageTitle", "Orders for Customer #" + customerNumber);
        return "order/orders";
    }

    // =========================================================================
    // GET /orders/{orderNumber}/items — Show all items for an order
    // =========================================================================
    @GetMapping("/{orderNumber}/items")
    public String getOrderItems(
            @PathVariable
            @Positive(message = "Order number must be positive")
            Integer orderNumber,
            Model model) {
        List<OrderDetailResponseDTO> items = orderService.getItems(orderNumber);
        BigDecimal orderTotal = items.stream()
                .map(OrderDetailResponseDTO::getLineTotal)
                .filter(total -> total != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("items", items);
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("itemCount", items.size());
        model.addAttribute("hasItems", !items.isEmpty());
        model.addAttribute("orderTotal", orderTotal);
        return "order/items";
    }

    // =========================================================================
    // GET /orders/{orderNumber}/items/new — Show add item form
    // =========================================================================
    @GetMapping("/{orderNumber}/items/new")
    public String showAddItemForm(
            @PathVariable
            @Positive(message = "Order number must be positive")
            Integer orderNumber,
            Model model) {
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("itemDTO", new OrderDetailRequestDTO());
        return "order/item-add";
    }

    // =========================================================================
    // POST /orders/{orderNumber}/items/new — Submit add item form (simulates POST)
    // =========================================================================
    @PostMapping("/{orderNumber}/items/new")
    public String addItemToOrder(
                                 @PathVariable
                                 @Positive(message = "Order number must be positive")
                                 Integer orderNumber,
                                 @Valid @ModelAttribute("itemDTO") OrderDetailRequestDTO dto) {
        orderService.addItem(orderNumber, dto);
        return "redirect:/orders/" + orderNumber + "/items";
    }

    // =========================================================================
    // GET /orders/{orderNumber}/items/{productCode}/edit — Show edit item form
    // =========================================================================
    @GetMapping("/{orderNumber}/items/{productCode}/edit")
    public String showEditItemForm(
                                   @PathVariable
                                   @Positive(message = "Order number must be positive")
                                   Integer orderNumber,
                                   @PathVariable
                                   @NotBlank(message = "Product code is required")
                                   @Size(max = 15, message = "Product code must not exceed 15 characters")
                                   String productCode,
                                   Model model) {
        // Get all items and find the one matching productCode
        List<OrderDetailResponseDTO> items = orderService.getItems(orderNumber);
        OrderDetailResponseDTO existing = items.stream()
                .filter(i -> i.getProductCode().equals(productCode))
                .findFirst()
                .orElseThrow();

        OrderDetailRequestDTO dto = new OrderDetailRequestDTO();
        dto.setProductCode(existing.getProductCode());
        dto.setQuantityOrdered(existing.getQuantityOrdered());
        dto.setPriceEach(existing.getPriceEach());
        dto.setOrderLineNumber(existing.getOrderLineNumber());

        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("productCode", productCode);
        model.addAttribute("itemDTO", dto);
        return "order/item-edit";
    }

    // =========================================================================
    // POST /orders/{orderNumber}/items/{productCode}/edit — Submit edit item
    // =========================================================================
    @PostMapping("/{orderNumber}/items/{productCode}/edit")
    public String updateOrderItem(
                                  @PathVariable
                                  @Positive(message = "Order number must be positive")
                                  Integer orderNumber,
                                  @PathVariable
                                  @NotBlank(message = "Product code is required")
                                  @Size(max = 15, message = "Product code must not exceed 15 characters")
                                  String productCode,
                                  @Valid @ModelAttribute("itemDTO") OrderDetailRequestDTO dto) {
        orderService.updateItem(orderNumber, productCode, dto);
        return "redirect:/orders/" + orderNumber + "/items";
    }

    // =========================================================================
    // POST /orders/{orderNumber}/items/{productCode}/delete — Delete item
    // (browsers can't send DELETE, so we use POST with _method workaround)
    // =========================================================================
    @PostMapping("/{orderNumber}/items/{productCode}/delete")
    public String deleteOrderItem(
                                  @PathVariable
                                  @Positive(message = "Order number must be positive")
                                  Integer orderNumber,
                                  @PathVariable
                                  @NotBlank(message = "Product code is required")
                                  @Size(max = 15, message = "Product code must not exceed 15 characters")
                                  String productCode) {
        orderService.removeItem(orderNumber, productCode);
        return "redirect:/orders/" + orderNumber + "/items";
    }
}
