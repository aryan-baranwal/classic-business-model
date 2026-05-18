package com.classicmodels.repository;

import com.classicmodels.entity.OrderDetail;
import com.classicmodels.entity.OrderDetailId;
import com.classicmodels.entity.Order;
import com.classicmodels.entity.Customer;
import com.classicmodels.entity.Product;
import com.classicmodels.repository.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// @DataJpaTest loads only JPA related components for testing
// Uses in-memory H2 database by default
@DataJpaTest
public class OrderDetailRepositoryTest {

    // Inject the repository to be tested
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // TestEntityManager used to set up test data
    @Autowired
    private TestEntityManager entityManager;

    // Test data variables
    private Order testOrder;
    private Product testProduct1;
    private Product testProduct2;
    private OrderDetail orderDetail1;
    private OrderDetail orderDetail2;

    // @BeforeEach runs before every test method
    // Sets up fresh test data for each test
    @BeforeEach
    public void setUp() {

        // Create and persist a test customer
        Customer customer = new Customer();
        customer.setCustomerNumber(103);
        customer.setCustomerName("Test Customer");
        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");
        customer.setPhone("1234567890");
        customer.setAddressLine1("123 Test Street");
        customer.setCity("Test City");
        customer.setCountry("USA");
        entityManager.persist(customer);

        // Create and persist a test order
        testOrder = new Order();
        testOrder.setOrderNumber(10001);
        testOrder.setOrderDate(LocalDate.of(2024, 1, 10));
        testOrder.setRequiredDate(LocalDate.of(2024, 1, 20));
        testOrder.setStatus("Shipped");
        testOrder.setCustomer(customer);
        entityManager.persist(testOrder);

        // Create and persist first test product
        testProduct1 = new Product();
        testProduct1.setProductCode("S18_1749");
        testProduct1.setProductName("1917 Grand Touring Sedan");
        entityManager.persist(testProduct1);

        // Create and persist second test product
        testProduct2 = new Product();
        testProduct2.setProductCode("S24_2972");
        testProduct2.setProductName("1982 Lamborghini Diablo");
        entityManager.persist(testProduct2);

        // Create and persist first order detail line item
        orderDetail1 = new OrderDetail();
        orderDetail1.setId(new OrderDetailId(10001, "S18_1749"));
        orderDetail1.setQuantityOrdered(30);
        orderDetail1.setPriceEach(new BigDecimal("136.00"));
        orderDetail1.setOrderLineNumber((short) 1);
        orderDetail1.setOrder(testOrder);
        orderDetail1.setProduct(testProduct1);
        entityManager.persist(orderDetail1);

        // Create and persist second order detail line item
        orderDetail2 = new OrderDetail();
        orderDetail2.setId(new OrderDetailId(10001, "S24_2972"));
        orderDetail2.setQuantityOrdered(50);
        orderDetail2.setPriceEach(new BigDecimal("55.09"));
        orderDetail2.setOrderLineNumber((short) 2);
        orderDetail2.setOrder(testOrder);
        orderDetail2.setProduct(testProduct2);
        entityManager.persist(orderDetail2);

        // Flush to sync with in-memory database
        entityManager.flush();
    }

    // ============================================================
    // TEST 1: Save a new order detail
    // Purpose: Verify a new order line item is saved successfully
    // ============================================================
    @Test
    public void testSaveOrderDetail() {
        // Arrange — create a new order detail
        OrderDetail newDetail = new OrderDetail();
        newDetail.setId(new OrderDetailId(10001, "S700_3505"));
        newDetail.setQuantityOrdered(20);
        newDetail.setPriceEach(new BigDecimal("100.00"));
        newDetail.setOrderLineNumber((short) 3);
        newDetail.setOrder(testOrder);

        // Act — save the order detail
        OrderDetail saved = orderDetailRepository.save(newDetail);

        // Assert — verify the order detail was saved correctly
        assertNotNull(saved);
        assertEquals("S700_3505", saved.getId().getProductCode());
        assertEquals(20, saved.getQuantityOrdered());
    }

    // ============================================================
    // TEST 2: Find order detail by composite ID
    // Purpose: Verify retrieval by composite key works correctly
    // ============================================================
    @Test
    public void testFindOrderDetailById() {
        // Arrange — create the composite key
        OrderDetailId id = new OrderDetailId(10001, "S18_1749");

        // Act — find the order detail by composite key
        Optional<OrderDetail> found = orderDetailRepository.findById(id);

        // Assert — verify the order detail exists with correct data
        assertTrue(found.isPresent());
        assertEquals(30, found.get().getQuantityOrdered());
        assertEquals(new BigDecimal("136.00"), found.get().getPriceEach());
    }

    // ============================================================
    // TEST 3: Find order detail by composite ID — Not Found
    // Purpose: Verify empty Optional returned for non-existent item
    // ============================================================
    @Test
    public void testFindOrderDetailByIdNotFound() {
        // Arrange — create a composite key that does not exist
        OrderDetailId id = new OrderDetailId(99999, "INVALID");

        // Act — search for the non-existent order detail
        Optional<OrderDetail> found = orderDetailRepository.findById(id);

        // Assert — verify that no order detail was found
        assertFalse(found.isPresent());
    }

    // ============================================================
    // TEST 4: Find all order details by order number (JPQL)
    // Purpose: Verify custom JPQL retrieves all items for an order
    // ============================================================
    @Test
    public void testFindByOrderNumber() {
        // Act — find all line items for order 10001
        List<OrderDetail> items = orderDetailRepository.findByOrderNumber(10001);

        // Assert — verify both line items are returned
        assertNotNull(items);
        assertEquals(2, items.size());
    }

    // ============================================================
    // TEST 5: Update order detail quantity and price
    // Purpose: Verify that quantity and price can be updated
    // ============================================================
    @Test
    public void testUpdateOrderDetail() {
        // Arrange — fetch the existing order detail
        OrderDetailId id = new OrderDetailId(10001, "S18_1749");
        OrderDetail existing = orderDetailRepository.findById(id).get();

        // Act — update quantity and price
        existing.setQuantityOrdered(50);
        existing.setPriceEach(new BigDecimal("150.00"));
        OrderDetail updated = orderDetailRepository.save(existing);

        // Assert — verify the values were updated correctly
        assertEquals(50, updated.getQuantityOrdered());
        assertEquals(new BigDecimal("150.00"), updated.getPriceEach());
    }

    // ============================================================
    // TEST 6: Delete order detail by composite ID
    // Purpose: Verify that a line item can be removed from an order
    // ============================================================
    @Test
    public void testDeleteOrderDetail() {
        // Arrange — create the composite key for deletion
        OrderDetailId id = new OrderDetailId(10001, "S18_1749");

        // Act — delete the order detail
        orderDetailRepository.deleteById(id);

        // Assert — verify the order detail no longer exists
        Optional<OrderDetail> deleted = orderDetailRepository.findById(id);
        assertFalse(deleted.isPresent());
    }

    // ============================================================
    // TEST 7: Find all order details
    // Purpose: Verify that all order details are retrieved
    // ============================================================
    @Test
    public void testFindAllOrderDetails() {
        // Act — retrieve all order details
        List<OrderDetail> allDetails = orderDetailRepository.findAll();

        // Assert — verify both test details are returned
        assertNotNull(allDetails);
        assertEquals(2, allDetails.size());
    }
}