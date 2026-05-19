package com.classicmodels.repository;

import com.classicmodels.entity.Customer;
import com.classicmodels.entity.Order;
import com.classicmodels.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// @DataJpaTest — loads only JPA related components
// Uses in-memory H2 database for isolated testing
@DataJpaTest
public class OrderRepositoryTest {

    // Repository being tested
    @Autowired
    private OrderRepository orderRepository;

    // Used to set up and persist test data directly
    @Autowired
    private TestEntityManager entityManager;

    // Shared test data
    private Customer testCustomer;
    private Order order1;
    private Order order2;

    // Runs before every test method
    // Sets up fresh test data for each test
    @BeforeEach
    public void setUp() {

        // Create and persist a test customer
        // Required as orders FK references customers table
        testCustomer = new Customer();
        testCustomer.setCustomerNumber(103);
        testCustomer.setCustomerName("Test Customer");
        testCustomer.setContactFirstName("John");
        testCustomer.setContactLastName("Doe");
        testCustomer.setPhone("1234567890");
        testCustomer.setAddressLine1("123 Test Street");
        testCustomer.setCity("Test City");
        testCustomer.setCountry("USA");
        entityManager.persist(testCustomer);

        // Create and persist first test order — Shipped status
        order1 = new Order();
        order1.setOrderNumber(10001);
        order1.setOrderDate(LocalDate.of(2024, 1, 10));
        order1.setRequiredDate(LocalDate.of(2024, 1, 20));
        order1.setShippedDate(LocalDate.of(2024, 1, 15));
        order1.setStatus("Shipped");
        order1.setComments("Test order 1");
        order1.setCustomer(testCustomer);
        entityManager.persist(order1);

        // Create and persist second test order — On Hold status
        order2 = new Order();
        order2.setOrderNumber(10002);
        order2.setOrderDate(LocalDate.of(2024, 2, 5));
        order2.setRequiredDate(LocalDate.of(2024, 2, 20));
        order2.setShippedDate(null);
        order2.setStatus("On Hold");
        order2.setComments("Test order 2");
        order2.setCustomer(testCustomer);
        entityManager.persist(order2);

        // Flush to sync persisted data with in-memory database
        entityManager.flush();
    }

    // ============================================================
    // TEST 1: Save a new order
    // API: POST /api/orders
    // Purpose: Verify a new order is saved successfully
    // ============================================================
    @Test
    public void testSaveOrder() {
        // Arrange
        Order newOrder = new Order();
        newOrder.setOrderNumber(10003);
        newOrder.setOrderDate(LocalDate.of(2024, 3, 1));
        newOrder.setRequiredDate(LocalDate.of(2024, 3, 15));
        newOrder.setStatus("In Process");
        newOrder.setCustomer(testCustomer);

        // Act
        Order saved = orderRepository.save(newOrder);

        // Assert
        assertNotNull(saved);
        assertEquals(10003, saved.getOrderNumber());
        assertEquals("In Process", saved.getStatus());
        assertEquals(LocalDate.of(2024, 3, 1), saved.getOrderDate());
    }

    // ============================================================
    // TEST 2: Find order by ID — Found
    // API: GET /api/orders/{orderNumber}
    // Purpose: Verify order is retrieved correctly by orderNumber
    // ============================================================
    @Test
    public void testFindOrderByIdFound() {
        // Act
        Optional<Order> found = orderRepository.findById(10001);

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Shipped", found.get().getStatus());
        assertEquals(LocalDate.of(2024, 1, 10), found.get().getOrderDate());
        assertEquals(103, found.get().getCustomer().getCustomerNumber());
    }

    // ============================================================
    // TEST 3: Find order by ID — Not Found
    // API: GET /api/orders/{orderNumber}
    // Purpose: Verify empty Optional returned for non-existent order
    // ============================================================
    @Test
    public void testFindOrderByIdNotFound() {
        // Act
        Optional<Order> found = orderRepository.findById(99999);

        // Assert
        assertFalse(found.isPresent());
    }

    // ============================================================
    // TEST 4: Find all orders
    // API: GET /api/orders
    // Purpose: Verify all orders are retrieved correctly
    // ============================================================
    @Test
    public void testFindAllOrders() {
        // Act
        List<Order> orders = orderRepository.findAll();

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    // ============================================================
    // TEST 5: Find orders by customer number — JPQL
    // API: GET /api/customers/{customerNumber}/orders
    // Purpose: Verify custom JPQL retrieves all orders for a customer
    // ============================================================
    @Test
    public void testFindByCustomerNumber() {
        // Act
        List<Order> orders = orderRepository.findByCustomer_CustomerNumber(363);

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
        orders.forEach(o -> assertEquals(103, o.getCustomer().getCustomerNumber()));
    }

    // ============================================================
    // TEST 6: Find orders by customer number — No Results
    // API: GET /api/customers/{customerNumber}/orders
    // Purpose: Verify empty list returned for customer with no orders
    // ============================================================
    @Test
    public void testFindByCustomerNumberNoResults() {
        // Act
        List<Order> orders = orderRepository.findByCustomer_CustomerNumber(99999);

        // Assert
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    // ============================================================
    // TEST 7: Search orders by status — JPQL
    // API: GET /api/orders/search?status=Shipped
    // Purpose: Verify search filters correctly by status
    // ============================================================
    @Test
    public void testSearchOrdersByStatus() {
        // Act
        List<Order> orders = orderRepository.searchOrders("Shipped", null, null);

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("Shipped", orders.get(0).getStatus());
    }

    // ============================================================
    // TEST 8: Search orders by date range — JPQL
    // API: GET /api/orders/search?fromDate=&toDate=
    // Purpose: Verify search filters correctly by date range
    // ============================================================
    @Test
    public void testSearchOrdersByDateRange() {
        // Act
        List<Order> orders = orderRepository.searchOrders(
                null,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)
        );

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(10001, orders.get(0).getOrderNumber());
    }

    // ============================================================
    // TEST 9: Search orders by status and date range — JPQL
    // API: GET /api/orders/search?status=On Hold&fromDate=&toDate=
    // Purpose: Verify search filters correctly by both params together
    // ============================================================
    @Test
    public void testSearchOrdersByStatusAndDateRange() {
        // Act
        List<Order> orders = orderRepository.searchOrders(
                "On Hold",
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 2, 28)
        );

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(10002, orders.get(0).getOrderNumber());
        assertEquals("On Hold", orders.get(0).getStatus());
    }

    // ============================================================
    // TEST 10: Update order dates and comments
    // API: PUT /api/orders/{orderNumber}
    // Purpose: Verify order fields are updated correctly
    // ============================================================
    @Test
    public void testUpdateOrder() {
        // Arrange
        Order existing = orderRepository.findById(10001).get();
        existing.setComments("Updated comments");
        existing.setShippedDate(LocalDate.of(2024, 1, 18));

        // Act
        Order updated = orderRepository.save(existing);

        // Assert
        assertEquals("Updated comments", updated.getComments());
        assertEquals(LocalDate.of(2024, 1, 18), updated.getShippedDate());
    }

    // ============================================================
    // TEST 11: Update order status
    // API: PATCH /api/orders/{orderNumber}/status
    // Purpose: Verify only the status field is updated correctly
    // ============================================================
    @Test
    public void testUpdateOrderStatus() {
        // Arrange
        Order existing = orderRepository.findById(10002).get();
        existing.setStatus("Shipped");

        // Act
        Order updated = orderRepository.save(existing);

        // Assert
        assertEquals("Shipped", updated.getStatus());
        assertEquals(10002, updated.getOrderNumber());
    }

    // ============================================================
    // TEST 12: Delete order by ID
    // Purpose: Verify order is deleted and no longer retrievable
    // ============================================================
    @Test
    public void testDeleteOrder() {
        // Act
        orderRepository.deleteById(10001);

        // Assert
        Optional<Order> deleted = orderRepository.findById(10001);
        assertFalse(deleted.isPresent());
    }
}