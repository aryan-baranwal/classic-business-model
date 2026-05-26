package com.classicmodels.controller;

import com.classicmodels.dto.CustomerRequestDTO;
import com.classicmodels.dto.CustomerResponseDTO;
import com.classicmodels.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    // Constructor injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // POST /api/customers — Create a new customer
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Validated({
                    Default.class,
                    CustomerRequestDTO.Create.class
            })
            @RequestBody CustomerRequestDTO request) {
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
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber) {
        return ResponseEntity.ok(customerService.getCustomerById(customerNumber));
    }

    // PUT /api/customers/{customerNumber} — Update customer
    @PutMapping("/{customerNumber}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            @Valid @RequestBody CustomerRequestDTO request) {
        return ResponseEntity.ok(customerService.updateCustomer(customerNumber, request));
    }

    // DELETE /api/customers/{customerNumber} — Delete customer
// DELETE /api/customers/{customerNumber} — Delete customer
    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber) {
        return ResponseEntity.ok(customerService.deleteCustomer(customerNumber));
    }
    // GET /api/customers/{customerNumber}/credit-limit — Get credit limit
    @GetMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<BigDecimal> getCreditLimit(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber) {
        return ResponseEntity.ok(customerService.getCreditLimit(customerNumber));
    }

    // PUT /api/customers/{customerNumber}/credit-limit — Update credit limit
    @PutMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<CustomerResponseDTO> updateCreditLimit(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            @RequestBody
            @PositiveOrZero(message = "Credit limit cannot be negative")
            BigDecimal creditLimit) {
        return ResponseEntity.ok(customerService.updateCreditLimit(customerNumber, creditLimit));
    }

    // GET /api/customers/search?country=&city= — Search by country and city
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomers(
            @RequestParam
            @NotBlank(message = "Country must not be blank")
            String country,
            @RequestParam
            @NotBlank(message = "City must not be blank")
            String city) {
        return ResponseEntity.ok(customerService.searchByCountryAndCity(country, city));
    }

}
