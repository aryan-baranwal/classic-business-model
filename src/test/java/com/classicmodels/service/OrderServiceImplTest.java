package com.classicmodels.service;

import com.classicmodels.entity.Customer;
import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.OrderDetailId;
import com.classicmodels.entity.Order;
import com.classicmodels.repository.OrderDetailRepository;
import com.classicmodels.repository.OrderRepository;
import com.classicmodels.service.impl.OrderServiceImpl;
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

    @InjectMocks
    private OrderServiceImpl orderService;

    // ── shared fixtures ───────────────────────────────────────────────────────

    private Order buildOrder(Integer orderNumber, Integer customerNumber, String status) {
        Customer customer = new Customer();
        customer.setCustomerNumber(customerNumber);

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setOrderDate(LocalDate.of(2024, 1, 10));
        order.setRequiredDate(LocalDate.of(2024, 1, 20));
        order.setShippedDate(LocalDate.of(2024, 1, 15));
        order.setStatus(status);
        order.setComments("Test comment");
        order.setCustomer(customer);
        return order;
    }

    private OrderDetail buildDetail(Integer orderNumber, String productCode,
                                    int qty, double price) {
        OrderDetailId id = new OrderDetailId(orderNumber, productCode);

        OrderDetail detail = new OrderDetail();
        detail.setId(id);
        detail.setQuantityOrdered(qty);
        detail.setPriceEach(BigDecimal.valueOf(price));
        return detail;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  createOrder
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("createOrder – saves and returns the order")
    void createOrder_savesAndReturns() {
        Order order = buildOrder(10100, 363, "In Process");
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertThat(result).isEqualTo(order);
        verify(orderRepository).save(order);
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getAllOrders
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getAllOrders – returns all orders from repository")
    void getAllOrders_returnsAll() {
        List<Order> orders = List.of(
                buildOrder(10100, 363, "Shipped"),
                buildOrder(10101, 128, "Shipped")
        );
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertThat(result).hasSize(2);
        verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("getAllOrders – returns empty list when no orders exist")
    void getAllOrders_empty() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<Order> result = orderService.getAllOrders();

        assertThat(result).isEmpty();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getOrderById
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getOrderById – returns order when found")
    void getOrderById_found() {
        Order order = buildOrder(10100, 363, "Shipped");
        when(orderRepository.findById(10100)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(10100);

        assertThat(result.getOrderNumber()).isEqualTo(10100);
    }

    @Test
    @DisplayName("getOrderById – throws RuntimeException when not found")
    void getOrderById_notFound() {
        when(orderRepository.findById(9999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(9999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Order not found");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  updateOrder
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("updateOrder – updates dates and comments, returns saved entity")
    void updateOrder_updatesFields() {
        Order existing = buildOrder(10100, 363, "Shipped");
        when(orderRepository.findById(10100)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Order patch = new Order();
        patch.setOrderDate(LocalDate.of(2024, 2, 1));
        patch.setRequiredDate(LocalDate.of(2024, 2, 10));
        patch.setShippedDate(LocalDate.of(2024, 2, 5));
        patch.setComments("Updated comment");

        Order result = orderService.updateOrder(10100, patch);

        assertThat(result.getComments()).isEqualTo("Updated comment");
        assertThat(result.getOrderDate()).isEqualTo(LocalDate.of(2024, 2, 1));
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  updateOrderStatus
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("updateOrderStatus – changes status to Shipped")
    void updateOrderStatus_toShipped() {
        Order existing = buildOrder(10100, 363, "In Process");
        when(orderRepository.findById(10100)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Order result = orderService.updateOrderStatus(10100, "Shipped");

        assertThat(result.getStatus()).isEqualTo("Shipped");
    }

    @Test
    @DisplayName("updateOrderStatus – changes status to Cancelled")
    void updateOrderStatus_toCancelled() {
        Order existing = buildOrder(10167, 448, "In Process");
        when(orderRepository.findById(10167)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Order result = orderService.updateOrderStatus(10167, "Cancelled");

        assertThat(result.getStatus()).isEqualTo("Cancelled");
    }

    @Test
    @DisplayName("updateOrderStatus – changes status to Resolved")
    void updateOrderStatus_toResolved() {
        Order existing = buildOrder(10164, 452, "Disputed");
        when(orderRepository.findById(10164)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Order result = orderService.updateOrderStatus(10164, "Resolved");

        assertThat(result.getStatus()).isEqualTo("Resolved");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getOrdersByCustomer
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getOrdersByCustomer – filters only orders for that customer")
    void getOrdersByCustomer_filtersCorrectly() {
        List<Order> all = List.of(
                buildOrder(10100, 363, "Shipped"),
                buildOrder(10101, 128, "Shipped"),
                buildOrder(10200, 363, "Shipped")
        );
        when(orderRepository.findAll()).thenReturn(all);

        List<Order> result = orderService.getOrdersByCustomer(363);

        assertThat(result).hasSize(2)
                .allMatch(o -> o.getCustomer().getCustomerNumber().equals(363));
    }

    @Test
    @DisplayName("getOrdersByCustomer – returns empty list when customer has no orders")
    void getOrdersByCustomer_noOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<Order> result = orderService.getOrdersByCustomer(9999);

        assertThat(result).isEmpty();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  searchOrders
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("searchOrders – filters by status only")
    void searchOrders_byStatusOnly() {
        List<Order> all = List.of(
                buildOrder(10100, 363, "Shipped"),
                buildOrder(10167, 448, "Cancelled"),
                buildOrder(10420, 282, "In Process")
        );
        when(orderRepository.findAll()).thenReturn(all);

        List<Order> result = orderService.searchOrders("Shipped", null, null);

        assertThat(result).hasSize(1)
                .extracting(Order::getStatus)
                .containsOnly("Shipped");
    }

    @Test
    @DisplayName("searchOrders – filters by status and date range")
    void searchOrders_byStatusAndDateRange() {
        Order o1 = buildOrder(10100, 363, "Shipped"); // orderDate 2024-01-10
        Order o2 = buildOrder(10101, 128, "Shipped");
        o2.setOrderDate(LocalDate.of(2024, 3, 1));           // outside range

        when(orderRepository.findAll()).thenReturn(List.of(o1, o2));

        List<Order> result = orderService.searchOrders(
                "Shipped",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31));

        assertThat(result).hasSize(1)
                .extracting(Order::getOrderNumber)
                .containsOnly(10100);
    }

    @Test
    @DisplayName("searchOrders – null status returns all within date range")
    void searchOrders_nullStatus() {
        List<Order> all = List.of(
                buildOrder(10100, 363, "Shipped"),
                buildOrder(10167, 448, "Cancelled")
        );
        when(orderRepository.findAll()).thenReturn(all);

        List<Order> result = orderService.searchOrders(null, null, null);

        assertThat(result).hasSize(2);
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  addItemToOrder
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("addItemToOrder – sets order reference and saves item")
    void addItemToOrder_savesItem() {
        Order order = buildOrder(10100, 363, "In Process");
        when(orderRepository.findById(10100)).thenReturn(Optional.of(order));

        OrderDetail item = buildDetail(10100, "S18_1749", 30, 136.00);
        when(orderDetailRepository.save(item)).thenReturn(item);

        OrderDetail result = orderService.addItemToOrder(10100, item);

        assertThat(result.getOrder()).isEqualTo(order);
        assertThat(result.getId().getOrderNumber()).isEqualTo(10100);
        verify(orderDetailRepository).save(item);
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  getOrderItems
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("getOrderItems – returns only items belonging to the order")
    void getOrderItems_filtersCorrectly() {
        when(orderRepository.findById(10100))
                .thenReturn(Optional.of(buildOrder(10100, 363, "Shipped")));

        List<OrderDetail> allDetails = List.of(
                buildDetail(10100, "S18_1749", 30, 136.00),
                buildDetail(10100, "S18_2248", 50, 55.09),
                buildDetail(10101, "S18_2325", 25, 108.06)  // different order
        );
        when(orderDetailRepository.findAll()).thenReturn(allDetails);

        List<OrderDetail> result = orderService.getOrderItems(10100);

        assertThat(result).hasSize(2)
                .allMatch(d -> d.getId().getOrderNumber().equals(10100));
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  updateOrderItem
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("updateOrderItem – updates quantity and price")
    void updateOrderItem_updatesFields() {
        OrderDetailId id = new OrderDetailId(10100, "S18_1749");
        OrderDetail existing = buildDetail(10100, "S18_1749", 30, 136.00);
        when(orderDetailRepository.findById(id)).thenReturn(Optional.of(existing));
        when(orderDetailRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        OrderDetail patch = buildDetail(10100, "S18_1749", 45, 120.00);
        OrderDetail result = orderService.updateOrderItem(10100, "S18_1749", patch);

        assertThat(result.getQuantityOrdered()).isEqualTo(45);
        assertThat(result.getPriceEach()).isEqualByComparingTo(BigDecimal.valueOf(120.00));
    }

    @Test
    @DisplayName("updateOrderItem – throws when item not found")
    void updateOrderItem_notFound() {
        OrderDetailId id = new OrderDetailId(10100, "S99_9999");
        when(orderDetailRepository.findById(id)).thenReturn(Optional.empty());

        OrderDetail patch = new OrderDetail();
        assertThatThrownBy(() -> orderService.updateOrderItem(10100, "S99_9999", patch))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("OrderDetail not found");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  removeOrderItem
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("removeOrderItem – deletes when item exists")
    void removeOrderItem_deletesItem() {
        OrderDetailId id = new OrderDetailId(10100, "S18_1749");
        when(orderDetailRepository.existsById(id)).thenReturn(true);

        orderService.removeOrderItem(10100, "S18_1749");

        verify(orderDetailRepository).deleteById(id);
    }

    @Test
    @DisplayName("removeOrderItem – throws when item not found")
    void removeOrderItem_notFound() {
        OrderDetailId id = new OrderDetailId(10100, "S99_9999");
        when(orderDetailRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> orderService.removeOrderItem(10100, "S99_9999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("OrderDetail not found");

        verify(orderDetailRepository, never()).deleteById(any());
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  calculateOrderValue
    // ═══════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("calculateOrderValue – sums priceEach * quantityOrdered correctly")
    void calculateOrderValue_correct() {
        when(orderRepository.findById(10100))
                .thenReturn(Optional.of(buildOrder(10100, 363, "Shipped")));

        // Real data from schema: (30*136.00) + (50*55.09) + (22*75.46) + (49*35.29)
        // = 4080.00 + 2754.50 + 1660.12 + 1729.21 = 10223.83
        List<OrderDetail> details = List.of(
                buildDetail(10100, "S18_1749", 30, 136.00),
                buildDetail(10100, "S18_2248", 50, 55.09),
                buildDetail(10100, "S18_4409", 22, 75.46),
                buildDetail(10100, "S24_3969", 49, 35.29)
        );
        when(orderDetailRepository.findAll()).thenReturn(details);

        BigDecimal total = orderService.calculateOrderValue(10100);

        assertThat(total).isEqualByComparingTo(new BigDecimal("10223.83"));
    }

    @Test
    @DisplayName("calculateOrderValue – returns ZERO when order has no items")
    void calculateOrderValue_emptyOrder() {
        when(orderRepository.findById(10100))
                .thenReturn(Optional.of(buildOrder(10100, 363, "In Process")));
        when(orderDetailRepository.findAll()).thenReturn(Collections.emptyList());

        BigDecimal total = orderService.calculateOrderValue(10100);

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("calculateOrderValue – throws when order does not exist")
    void calculateOrderValue_orderNotFound() {
        when(orderRepository.findById(9999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.calculateOrderValue(9999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Order not found");
    }
}