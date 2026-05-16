package com.classicmodels.repository;

import com.classicmodels.entity.Customer;
import com.classicmodels.entity.Payment;
import com.classicmodels.entity.PaymentId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // -----------------------------------------------
    // Helper method — builds a test Customer
    // -----------------------------------------------
    private Customer buildTestCustomer() {
        Customer customer = new Customer();
        customer.setCustomerNumber(999);
        customer.setCustomerName("Test Shop");
        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");
        customer.setPhone("1234567890");
        customer.setAddressLine1("123 Test Street");
        customer.setCity("Test City");
        customer.setCountry("Test Country");
        customer.setCreditLimit(new BigDecimal("5000.00"));
        return customer;
    }

    // -----------------------------------------------
    // Helper method — builds a test Payment
    // -----------------------------------------------
    private Payment buildTestPayment(Customer customer) {
        PaymentId paymentId = new PaymentId(999, "TEST001");
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(new BigDecimal("1500.00"));
        payment.setCustomer(customer);
        return payment;
    }

    // -----------------------------------------------
    // TEST 1: Save a payment (CREATE)
    // -----------------------------------------------
    @Test
    void testSavePayment() {
        Customer customer = customerRepository.save(buildTestCustomer());
        Payment payment = paymentRepository.save(buildTestPayment(customer));

        assertNotNull(payment);
        assertEquals("TEST001", payment.getId().getCheckNumber());
        System.out.println("✅ CREATE test passed: " + payment.getAmount());
    }

    // -----------------------------------------------
    // TEST 2: Find a payment by composite ID (READ)
    // -----------------------------------------------
    @Test
    void testFindPaymentById() {
        Customer customer = customerRepository.save(buildTestCustomer());
        paymentRepository.save(buildTestPayment(customer));

        PaymentId id = new PaymentId(999, "TEST001");
        Optional<Payment> found = paymentRepository.findById(id);

        assertTrue(found.isPresent());
        assertEquals(new BigDecimal("1500.00"), found.get().getAmount());
        System.out.println("✅ READ test passed: " + found.get().getAmount());
    }

    // -----------------------------------------------
    // TEST 3: Delete a payment (DELETE)
    // -----------------------------------------------
    @Test
    void testDeletePayment() {
        Customer customer = customerRepository.save(buildTestCustomer());
        paymentRepository.save(buildTestPayment(customer));

        PaymentId id = new PaymentId(999, "TEST001");
        paymentRepository.deleteById(id);

        Optional<Payment> deleted = paymentRepository.findById(id);
        assertFalse(deleted.isPresent());
        System.out.println("✅ DELETE test passed: Payment no longer exists");
    }
}