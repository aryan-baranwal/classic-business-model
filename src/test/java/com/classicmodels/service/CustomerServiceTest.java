package com.classicmodels.service;

import com.classicmodels.dto.CustomerRequestDTO;
import com.classicmodels.dto.CustomerResponseDTO;
import com.classicmodels.entity.Customer;
import com.classicmodels.exception.CustomerNotFoundException;
import com.classicmodels.repository.CustomerRepository;
import com.classicmodels.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    // Test data
    private Customer testCustomer;
    private CustomerRequestDTO testRequest;

    @BeforeEach
    void setUp() {
        // Build test Customer entity
        testCustomer = new Customer();
        testCustomer.setCustomerNumber(999);
        testCustomer.setCustomerName("Test Shop");
        testCustomer.setContactFirstName("John");
        testCustomer.setContactLastName("Doe");
        testCustomer.setPhone("1234567890");
        testCustomer.setAddressLine1("123 Test Street");
        testCustomer.setCity("Test City");
        testCustomer.setCountry("Test Country");
        testCustomer.setCreditLimit(new BigDecimal("5000.00"));

        // Build test RequestDTO
        testRequest = new CustomerRequestDTO();
        testRequest.setCustomerName("Test Shop");
        testRequest.setContactFirstName("John");
        testRequest.setContactLastName("Doe");
        testRequest.setPhone("1234567890");
        testRequest.setAddressLine1("123 Test Street");
        testRequest.setCity("Test City");
        testRequest.setCountry("Test Country");
        testRequest.setCreditLimit(new BigDecimal("5000.00"));
    }

    // -----------------------------------------------
    // TEST 1: Get all customers
    // -----------------------------------------------
    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(testCustomer));

        List<CustomerResponseDTO> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Shop", result.get(0).getCustomerName());
        System.out.println("✅ GET ALL test passed");
    }

    // -----------------------------------------------
    // TEST 2: Get customer by ID — success
    // -----------------------------------------------
    @Test
    void testGetCustomerById_Success() {
        when(customerRepository.findById(999))
                .thenReturn(Optional.of(testCustomer));

        CustomerResponseDTO result = customerService.getCustomerById(999);

        assertNotNull(result);
        assertEquals("Test Shop", result.getCustomerName());
        System.out.println("✅ GET BY ID test passed");
    }

    // -----------------------------------------------
    // TEST 3: Get customer by ID — not found
    // -----------------------------------------------
    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById(999));
        System.out.println("✅ NOT FOUND test passed");
    }

    // -----------------------------------------------
    // TEST 4: Create customer
    // -----------------------------------------------
    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(testCustomer);

        CustomerResponseDTO result = customerService.createCustomer(testRequest);

        assertNotNull(result);
        assertEquals("Test Shop", result.getCustomerName());
        verify(customerRepository, times(1)).save(any(Customer.class));
        System.out.println("✅ CREATE test passed");
    }

    // -----------------------------------------------
    // TEST 5: Update customer
    // -----------------------------------------------
    @Test
    void testUpdateCustomer() {
        when(customerRepository.findById(999))
                .thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(testCustomer);

        CustomerResponseDTO result = customerService.updateCustomer(999, testRequest);

        assertNotNull(result);
        verify(customerRepository, times(1)).save(any(Customer.class));
        System.out.println("✅ UPDATE test passed");
    }

    // -----------------------------------------------
    // TEST 6: Delete customer — success
    // -----------------------------------------------
    @Test
    void testDeleteCustomer() {
        when(customerRepository.findById(999))
                .thenReturn(Optional.of(testCustomer));

        customerService.deleteCustomer(999);

        verify(customerRepository, times(1)).delete(testCustomer);
        System.out.println("✅ DELETE test passed");
    }

    // -----------------------------------------------
    // TEST 7: Get credit limit
    // -----------------------------------------------
    @Test
    void testGetCreditLimit() {
        when(customerRepository.findById(999))
                .thenReturn(Optional.of(testCustomer));

        BigDecimal result = customerService.getCreditLimit(999);

        assertEquals(new BigDecimal("5000.00"), result);
        System.out.println("✅ GET CREDIT LIMIT test passed");
    }

    // -----------------------------------------------
    // TEST 8: Update credit limit
    // -----------------------------------------------
    @Test
    void testUpdateCreditLimit() {
        when(customerRepository.findById(999))
                .thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(testCustomer);

        CustomerResponseDTO result = customerService
                .updateCreditLimit(999, new BigDecimal("9000.00"));

        assertNotNull(result);
        verify(customerRepository, times(1)).save(any(Customer.class));
        System.out.println("✅ UPDATE CREDIT LIMIT test passed");
    }

    // -----------------------------------------------
    // TEST 9: Search by country and city
    // -----------------------------------------------
    @Test
    void testSearchByCountryAndCity() {
        when(customerRepository.findByCountryAndCity("France", "Nantes"))
                .thenReturn(List.of(testCustomer));

        List<CustomerResponseDTO> result = customerService
                .searchByCountryAndCity("France", "Nantes");

        assertNotNull(result);
        assertEquals(1, result.size());
        System.out.println("✅ SEARCH test passed");
    }

}