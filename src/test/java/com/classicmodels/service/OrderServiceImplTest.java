package com.classicmodels.service;

import com.classicmodels.dto.OrderDetailRequestDTO;
import com.classicmodels.dto.OrderDetailResponseDTO;
import com.classicmodels.dto.OrderRequestDTO;
import com.classicmodels.dto.OrderResponseDTO;
import com.classicmodels.dto.OrderStatusUpdateDTO;
import com.classicmodels.entity.Customer;
import com.classicmodels.entity.Order;
import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.OrderDetailId;
import com.classicmodels.entity.Product;
import com.classicmodels.repository.CustomerRepository;
import com.classicmodels.repository.OrderDetailRepository;
import com.classicmodels.repository.OrderRepository;
import com.classicmodels.repository.ProductRepository;
import com.classicmodels.service.impl.OrderServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    // ── shared fixtures ───────────────────────────────────────────────────────

    private Customer buildCustomer(Integer customerNumber) {
        Customer customer = new Customer();
        customer.setCustomerNumber(customerNumber);
        customer.setCustomerName("Test Customer " + customerNumber);
        return customer;
    }

    private Order buildOrder(Integer orderNumber, Integer customerNumber, String status) {
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setOrderDate(LocalDate.of(2024, 1, 10));
        order.setRequiredDate(LocalDate.of(2024, 1, 20));
        order.setShippedDate(LocalDate.of(2024, 1, 15));
        order.setStatus(status);
        order.setComments("Test comment");
        order.setCustomer(buildCustomer(customerNumber));
        return order;
    }

    private OrderDetail buildDetail(Integer orderNumber, String productCode,
                                    int qty, double price) {
        Product product = new Product();
        product.setProductCode(productCode);
        product.setProductName("Test Product " + productCode);

        OrderDetail detail = new OrderDetail();
        detail.setId(new OrderDetailId(orderNumber, productCode));
        detail.setQuantityOrdered(qty);
        detail.setPriceEach(BigDecimal.valueOf(price));
        detail.setOrderLineNumber((short) 1);
        detail.setProduct(product);
        return detail;
    }

    private OrderRequestDTO buildOrderRequestDTO(Integer orderNumber,
                                                 Integer customerNumber,
                                                 String status) {
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setOrderNumber(orderNumber);
        dto.setOrderDate(LocalDate.of(2024, 1, 10));
        dto.setRequiredDate(LocalDate.of(2024, 1, 20));
        dto.setShippedDate(LocalDate.of(2024, 1, 15));
        dto.setStatus(status);
        dto.setComments("Test comment");
        dto.setCustomerNumber(customerNumber);
        return dto;
    }

    private OrderDetailRequestDTO buildItemRequestDTO(String productCode,
                                                      int qty,
                                                      double price) {
        OrderDetailRequestDTO dto = new OrderDetailRequestDTO();
        dto.setProductCode(productCode);
        dto.setQuantityOrdered(qty);
        dto.setPriceEach(BigDecimal.valueOf(price));
        dto.setOrderLineNumber((short) 1);
        return dto;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  createOrder
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("createOrder – saves order and returns OrderResponseDTO")
    void createOrder_savesAndReturns() {
        OrderRequestDTO dto = buildOrderRequestDTO(10100, 363, "In Process");
        Customer customer = buildCustomer(363);
        Order savedOrder = buildOrder(10100, 363, "In Process");

        when(customerRepository.findById(363)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponseDTO result = orderService.createOrder(dto);

        assertThat(result).isNotNull();
        assertThat(result.getOrderNumber()).isEqualTo(10100);
        assertThat(result.getStatus()).isEqualTo("In Process");
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("createOrder – throws EntityNotFoundException when customer not found")
    void createOrder_customerNotFound() {
        OrderRequestDTO dto = buildOrderRequestDTO(10100, 9999, "In Process");
        when(customerRepository.findById(9999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Customer not found");

        verify(orderRepository, never()).save(any());
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getAllOrders
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getAllOrders – returns all orders as DTOs")
    void getAllOrders_returnsAll() {
        List<Order> orders = List.of(
                buildOrder(10100, 363, "Shipped"),
                buildOrder(10101, 128, "Shipped")
        );
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertThat(result).hasSize(2);
        verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("getAllOrders – returns empty list when no orders exist")
    void getAllOrders_empty() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertThat(result).isEmpty();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getOrderById
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getOrderById – returns OrderResponseDTO when found")
    void getOrderById_found() {
        Order order = buildOrder(10100, 363, "Shipped");
        when(orderRepository.findById(10100)).thenReturn(Optional.of(order));

        OrderResponseDTO result = orderService.getOrderById(10100);

        assertThat(result.getOrderNumber()).isEqualTo(10100);
        assertThat(result.getStatus()).isEqualTo("Shipped");
    }

    @Test
    @DisplayName("getOrderById – throws EntityNotFoundException when not found")
    void getOrderById_notFound() {
        when(orderRepository.findById(9999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(9999))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  updateOrder
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("updateOrder – updates fields and returns OrderResponseDTO")
    void updateOrder_updatesFields() {
        Order existing = buildOrder(10100, 363, "Shipped");
        when(orderRepository.findById(10100)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setOrderDate(LocalDate.of(2024, 2, 1));
        dto.setRequiredDate(LocalDate.of(2024, 2, 10));
        dto.setShippedDate(LocalDate.of(2024, 2, 5));
        dto.setComments("Updated comment");

        OrderResponseDTO result = orderService.updateOrder(10100, dto);

        assertThat(result.getComments()).isEqualTo("Updated comment");
        assertThat(result.getOrderDate()).isEqualTo(LocalDate.of(2024, 2, 1));
    }

    @Test
    @DisplayName("updateOrder – throws EntityNotFoundException when order not found")
    void updateOrder_notFound() {
        when(orderRepository.findById(9999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(9999, new OrderRequestDTO()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  updateOrderStatus
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("updateOrderStatus – changes status to Shipped")
    void updateOrderStatus_toShipped() {
        Order existing = buildOrder(10100, 363, "In Process");
        when(orderRepository.findById(10100)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        OrderStatusUpdateDTO dto = new OrderStatusUpdateDTO();
        dto.setStatus("Shipped");

        OrderResponseDTO result = orderService.updateOrderStatus(10100, dto);

        assertThat(result.getStatus()).isEqualTo("Shipped");
    }

    @Test
    @DisplayName("updateOrderStatus – changes status to Cancelled")
    void updateOrderStatus_toCancelled() {
        Order existing = buildOrder(10167, 448, "In Process");
        when(orderRepository.findById(10167)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        OrderStatusUpdateDTO dto = new OrderStatusUpdateDTO();
        dto.setStatus("Cancelled");

        OrderResponseDTO result = orderService.updateOrderStatus(10167, dto);

        assertThat(result.getStatus()).isEqualTo("Cancelled");
    }

    @Test
    @DisplayName("updateOrderStatus – changes status to Resolved")
    void updateOrderStatus_toResolved() {
        Order existing = buildOrder(10164, 452, "Disputed");
        when(orderRepository.findById(10164)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        OrderStatusUpdateDTO dto = new OrderStatusUpdateDTO();
        dto.setStatus("Resolved");

        OrderResponseDTO result = orderService.updateOrderStatus(10164, dto);

        assertThat(result.getStatus()).isEqualTo("Resolved");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getOrdersByCustomer
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getOrdersByCustomer – returns orders for given customer")
    void getOrdersByCustomer_filtersCorrectly() {
        List<Order> customerOrders = List.of(
                buildOrder(10100, 363, "Shipped"),
                buildOrder(10200, 363, "In Process")
        );
        when(orderRepository.findByCustomer_CustomerNumber(363))
                .thenReturn(customerOrders);

        List<OrderResponseDTO> result = orderService.getOrdersByCustomer(363);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(OrderResponseDTO::getCustomerNumber)
                .containsOnly(363);
    }

    @Test
    @DisplayName("getOrdersByCustomer – returns empty list when no orders")
    void getOrdersByCustomer_noOrders() {
        when(orderRepository.findByCustomer_CustomerNumber(9999))
                .thenReturn(Collections.emptyList());

        List<OrderResponseDTO> result = orderService.getOrdersByCustomer(9999);

        assertThat(result).isEmpty();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  searchOrders
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("searchOrders – filters by status only")
    void searchOrders_byStatusOnly() {
        List<Order> filtered = List.of(
                buildOrder(10100, 363, "Shipped")
        );
        when(orderRepository.searchOrders("Shipped", null, null))
                .thenReturn(filtered);

        List<OrderResponseDTO> result = orderService.searchOrders("Shipped", null, null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo("Shipped");
    }

    @Test
    @DisplayName("searchOrders – filters by status and date range")
    void searchOrders_byStatusAndDateRange() {
        List<Order> filtered = List.of(
                buildOrder(10100, 363, "Shipped")
        );
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to   = LocalDate.of(2024, 1, 31);

        when(orderRepository.searchOrders("Shipped", from, to)).thenReturn(filtered);

        List<OrderResponseDTO> result = orderService.searchOrders("Shipped", from, to);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOrderNumber()).isEqualTo(10100);
    }

    @Test
    @DisplayName("searchOrders – null params returns all orders")
    void searchOrders_allNull() {
        List<Order> all = List.of(
                buildOrder(10100, 363, "Shipped"),
                buildOrder(10167, 448, "Cancelled")
        );
        when(orderRepository.searchOrders(null, null, null)).thenReturn(all);

        List<OrderResponseDTO> result = orderService.searchOrders(null, null, null);

        assertThat(result).hasSize(2);
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  addItem
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("addItem – saves item and returns OrderDetailResponseDTO")
    void addItem_savesItem() {
        Order order = buildOrder(10100, 363, "In Process");
        Product product = new Product();
        product.setProductCode("S18_1749");
        product.setProductName("1917 Grand Touring Sedan");

        when(orderRepository.findById(10100)).thenReturn(Optional.of(order));
        when(productRepository.findById("S18_1749")).thenReturn(Optional.of(product));
        when(orderDetailRepository.existsById_OrderNumberAndId_ProductCode(
                10100, "S18_1749")).thenReturn(false);
        when(orderDetailRepository.save(any(OrderDetail.class)))
                .thenAnswer(inv -> {
                    OrderDetail d = inv.getArgument(0);
                    d.setId(new OrderDetailId(10100, "S18_1749"));
                    d.setProduct(product);
                    return d;
                });

        OrderDetailRequestDTO dto = buildItemRequestDTO("S18_1749", 30, 136.00);
        OrderDetailResponseDTO result = orderService.addItem(10100, dto);

        assertThat(result).isNotNull();
        assertThat(result.getProductCode()).isEqualTo("S18_1749");
        assertThat(result.getQuantityOrdered()).isEqualTo(30);
        verify(orderDetailRepository).save(any(OrderDetail.class));
    }

    @Test
    @DisplayName("addItem – throws when order not found")
    void addItem_orderNotFound() {
        when(orderRepository.findById(9999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.addItem(9999,
                buildItemRequestDTO("S18_1749", 30, 136.00)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    @Test
    @DisplayName("addItem – throws when product not found")
    void addItem_productNotFound() {
        when(orderRepository.findById(10100))
                .thenReturn(Optional.of(buildOrder(10100, 363, "In Process")));
        when(productRepository.findById("INVALID")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.addItem(10100,
                buildItemRequestDTO("INVALID", 10, 50.00)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    @DisplayName("addItem – throws when item already exists in order")
    void addItem_duplicateItem() {
        when(orderRepository.findById(10100))
                .thenReturn(Optional.of(buildOrder(10100, 363, "In Process")));
        when(productRepository.findById("S18_1749"))
                .thenReturn(Optional.of(new Product()));
        when(orderDetailRepository.existsById_OrderNumberAndId_ProductCode(
                10100, "S18_1749")).thenReturn(true);

        assertThatThrownBy(() -> orderService.addItem(10100,
                buildItemRequestDTO("S18_1749", 30, 136.00)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already exists in order");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getItems
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getItems – returns items for given order as DTOs")
    void getItems_returnsItems() {
        when(orderRepository.findById(10100))
                .thenReturn(Optional.of(buildOrder(10100, 363, "Shipped")));

        List<OrderDetail> details = List.of(
                buildDetail(10100, "S18_1749", 30, 136.00),
                buildDetail(10100, "S18_2248", 50, 55.09)
        );
        when(orderDetailRepository.findById_OrderNumber(10100)).thenReturn(details);

        List<OrderDetailResponseDTO> result = orderService.getItems(10100);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(OrderDetailResponseDTO::getProductCode)
                .containsExactlyInAnyOrder("S18_1749", "S18_2248");
    }

    @Test
    @DisplayName("getItems – throws when order not found")
    void getItems_orderNotFound() {
        when(orderRepository.findById(9999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getItems(9999))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  updateItem
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("updateItem – updates quantity and price, returns DTO")
    void updateItem_updatesFields() {
        OrderDetailId id = new OrderDetailId(10100, "S18_1749");
        OrderDetail existing = buildDetail(10100, "S18_1749", 30, 136.00);
        when(orderDetailRepository.findById(id)).thenReturn(Optional.of(existing));
        when(orderDetailRepository.save(any(OrderDetail.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        OrderDetailRequestDTO dto = buildItemRequestDTO("S18_1749", 45, 120.00);
        OrderDetailResponseDTO result = orderService.updateItem(10100, "S18_1749", dto);

        assertThat(result.getQuantityOrdered()).isEqualTo(45);
        assertThat(result.getPriceEach())
                .isEqualByComparingTo(BigDecimal.valueOf(120.00));
    }

    @Test
    @DisplayName("updateItem – throws EntityNotFoundException when item not found")
    void updateItem_notFound() {
        OrderDetailId id = new OrderDetailId(10100, "S99_9999");
        when(orderDetailRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateItem(10100, "S99_9999",
                new OrderDetailRequestDTO()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order detail not found");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  removeItem
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("removeItem – deletes item and returns deleted row as DTO")
    void removeItem_deletesItem() {
        OrderDetailId id = new OrderDetailId(10100, "S18_1749");
        OrderDetail existing = buildDetail(10100, "S18_1749", 30, 136.00);
        when(orderDetailRepository.findById(id)).thenReturn(Optional.of(existing));
        doNothing().when(orderDetailRepository)
                .deleteByOrderNumberAndProductCode(10100, "S18_1749");

        OrderDetailResponseDTO result = orderService.removeItem(10100, "S18_1749");

        assertThat(result).isNotNull();
        assertThat(result.getProductCode()).isEqualTo("S18_1749");
        verify(orderDetailRepository).deleteByOrderNumberAndProductCode(10100, "S18_1749");
    }

    @Test
    @DisplayName("removeItem – throws EntityNotFoundException when item not found")
    void removeItem_notFound() {
        OrderDetailId id = new OrderDetailId(10100, "S99_9999");
        when(orderDetailRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.removeItem(10100, "S99_9999"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order detail not found");

        verify(orderDetailRepository, never())
                .deleteByOrderNumberAndProductCode(anyInt(), anyString());
    }
}