package com.classicmodels.controller;

import com.classicmodels.dto.CustomerRequestDTO;
import com.classicmodels.dto.CustomerResponseDTO;
import com.classicmodels.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    // Constructor injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // POST /api/customers — Create a new customer
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(request));
    }

    // GET /api/customers — Get all customers
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // GET /api/customers/{customerNumber} — Get customer by ID
    @GetMapping("/{customerNumber}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @PathVariable Integer customerNumber) {
        return ResponseEntity.ok(customerService.getCustomerById(customerNumber));
    }

    // PUT /api/customers/{customerNumber} — Update customer
    @PutMapping("/{customerNumber}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable Integer customerNumber,
            @Valid @RequestBody CustomerRequestDTO request) {
        return ResponseEntity.ok(customerService.updateCustomer(customerNumber, request));
    }

    // DELETE /api/customers/{customerNumber} — Delete customer
// DELETE /api/customers/{customerNumber} — Delete customer
    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(
            @PathVariable Integer customerNumber) {
        return ResponseEntity.ok(customerService.deleteCustomer(customerNumber));
    }
    // GET /api/customers/{customerNumber}/credit-limit — Get credit limit
    @GetMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<BigDecimal> getCreditLimit(@PathVariable Integer customerNumber) {
        return ResponseEntity.ok(customerService.getCreditLimit(customerNumber));
    }

    // PUT /api/customers/{customerNumber}/credit-limit — Update credit limit
    @PutMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<CustomerResponseDTO> updateCreditLimit(
            @PathVariable Integer customerNumber,
            @RequestBody BigDecimal creditLimit) {
        return ResponseEntity.ok(customerService.updateCreditLimit(customerNumber, creditLimit));
    }

    // GET /api/customers/search?country=&city= — Search by country and city
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomers(
            @RequestParam String country,
            @RequestParam String city) {
        return ResponseEntity.ok(customerService.searchByCountryAndCity(country, city));
    }

}