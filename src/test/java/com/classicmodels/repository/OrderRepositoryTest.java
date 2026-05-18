package com.classicmodels.repository;

import com.classicmodels.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.mode=never",
        "spring.jpa.generate-ddl=true"
})
class OrderRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testCustomerExists() {

        Customer customer = new Customer();
        customer.setCustomerNumber(1);
        customer.setCustomerName("Test Customer");

        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");
        customer.setPhone("1234567890");
        customer.setAddressLine1("123 Street");
        customer.setCity("Mumbai");
        customer.setCountry("India");

        customerRepository.save(customer);

        assertTrue(customerRepository.findById(1).isPresent());
    }
}