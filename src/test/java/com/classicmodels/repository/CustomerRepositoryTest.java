package com.classicmodels.repository;

import com.classicmodels.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CustomerRepositoryTest {

    // Spring automatically injects the repository
    @Autowired
    private CustomerRepository customerRepository;

    // -----------------------------------------------
    // Helper method — builds a test Customer object
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
    // TEST 1: Save a customer (CREATE)
    // -----------------------------------------------
    @Test
    void testSaveCustomer() {
        Customer customer = buildTestCustomer();

        Customer saved = customerRepository.save(customer);

        // assertNotNull means "check that saved is NOT null"
        assertNotNull(saved);
        assertEquals(999, saved.getCustomerNumber());
        System.out.println("✅ CREATE test passed: " + saved.getCustomerName());
    }

    // -----------------------------------------------
    // TEST 2: Find a customer by ID (READ)
    // -----------------------------------------------
    @Test
    void testFindCustomerById() {
        // First save, then find
        customerRepository.save(buildTestCustomer());

        Optional<Customer> found = customerRepository.findById(999);

        // assertTrue means "check that this is true"
        assertTrue(found.isPresent());
        assertEquals("Test Shop", found.get().getCustomerName());
        System.out.println("✅ READ test passed: " + found.get().getCustomerName());
    }

    // -----------------------------------------------
    // TEST 3: Update a customer (UPDATE)
    // -----------------------------------------------
    @Test
    void testUpdateCustomer() {
        // First save
        customerRepository.save(buildTestCustomer());

        // Then update
        Optional<Customer> found = customerRepository.findById(999);
        assertTrue(found.isPresent());

        found.get().setCustomerName("Updated Test Shop");
        customerRepository.save(found.get());

        // Check update worked
        Optional<Customer> updated = customerRepository.findById(999);
        assertEquals("Updated Test Shop", updated.get().getCustomerName());
        System.out.println("✅ UPDATE test passed: " + updated.get().getCustomerName());
    }

    // -----------------------------------------------
    // TEST 4: Delete a customer (DELETE)
    // -----------------------------------------------
    @Test
    void testDeleteCustomer() {
        // First save
        customerRepository.save(buildTestCustomer());

        // Then delete
        customerRepository.deleteById(999);

        // Check it's gone
        Optional<Customer> deleted = customerRepository.findById(999);

        // assertFalse means "check that this is false"
        assertFalse(deleted.isPresent());
        System.out.println("✅ DELETE test passed: Customer no longer exists");
    }
}