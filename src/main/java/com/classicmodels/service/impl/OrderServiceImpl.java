package com.classicmodels.service.impl;

import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.OrderDetailId;
import com.classicmodels.entity.Order;
import com.classicmodels.repository.OrderDetailRepository;
import com.classicmodels.repository.OrderRepository;
import com.classicmodels.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    // ── Order CRUD ──────────────────────────────────────────────────────────

    @Override
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer orderNumber) {
        return orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderNumber));
    }

    @Override
    @Transactional
    public Order updateOrder(Integer orderNumber, Order updatedOrder) {
        Order existing = getOrderById(orderNumber);
        // Only updatable fields per PDF: dates and comments
        existing.setOrderDate(updatedOrder.getOrderDate());
        existing.setRequiredDate(updatedOrder.getRequiredDate());
        existing.setShippedDate(updatedOrder.getShippedDate());
        existing.setComments(updatedOrder.getComments());
        return orderRepository.save(existing);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Integer orderNumber, String status) {
        Order existing = getOrderById(orderNumber);
        existing.setStatus(status);
        return orderRepository.save(existing);
    }

    @Override
    public List<Order> getOrdersByCustomer(Integer customerNumber) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getCustomer() != null
                        && customerNumber.equals(o.getCustomer().getCustomerNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> searchOrders(String status, LocalDate fromDate, LocalDate toDate) {
        return orderRepository.findAll().stream()
                .filter(o -> status == null || status.equalsIgnoreCase(o.getStatus()))
                .filter(o -> fromDate == null || !o.getOrderDate().isBefore(fromDate))
                .filter(o -> toDate == null || !o.getOrderDate().isAfter(toDate))
                .collect(Collectors.toList());
    }

    // ── Order Line Items ─────────────────────────────────────────────────────

    @Override
    @Transactional
    public OrderDetail addItemToOrder(Integer orderNumber, OrderDetail item) {
        Order order = getOrderById(orderNumber);
        item.setOrder(order);
        // Ensure the embedded id carries the orderNumber
        if (item.getId() == null) {
            item.setId(new OrderDetailId());
        }
        item.getId().setOrderNumber(orderNumber);
        return orderDetailRepository.save(item);
    }

    @Override
    public List<OrderDetail> getOrderItems(Integer orderNumber) {
        // Verify the order exists first
        getOrderById(orderNumber);
        return orderDetailRepository.findAll().stream()
                .filter(d -> d.getId() != null
                        && orderNumber.equals(d.getId().getOrderNumber()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDetail updateOrderItem(Integer orderNumber, String productCode, OrderDetail updatedItem) {
        OrderDetailId id = new OrderDetailId(orderNumber, productCode);
        OrderDetail existing = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "OrderDetail not found for order " + orderNumber + " / product " + productCode));
        existing.setQuantityOrdered(updatedItem.getQuantityOrdered());
        existing.setPriceEach(updatedItem.getPriceEach());
        return orderDetailRepository.save(existing);
    }

    @Override
    @Transactional
    public void removeOrderItem(Integer orderNumber, String productCode) {
        OrderDetailId id = new OrderDetailId(orderNumber, productCode);
        if (!orderDetailRepository.existsById(id)) {
            throw new RuntimeException(
                    "OrderDetail not found for order " + orderNumber + " / product " + productCode);
        }
        orderDetailRepository.deleteById(id);
    }

    // ── Reporting ────────────────────────────────────────────────────────────

    @Override
    public BigDecimal calculateOrderValue(Integer orderNumber) {
        return getOrderItems(orderNumber).stream()
                .map(item -> item.getPriceEach()
                        .multiply(BigDecimal.valueOf(item.getQuantityOrdered())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}