package com.classicmodels.service;

import com.classicmodels.dto.CustomerRequestDTO;
import com.classicmodels.dto.CustomerResponseDTO;
import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    // Create a new customer
    CustomerResponseDTO createCustomer(CustomerRequestDTO request);

    // Get all customers
    List<CustomerResponseDTO> getAllCustomers();

    // Get one customer by their number
    CustomerResponseDTO getCustomerById(Integer customerNumber);

    // Update customer profile
    CustomerResponseDTO updateCustomer(Integer customerNumber, CustomerRequestDTO request);

    // Delete a customer - returns deleted customer data
    CustomerResponseDTO deleteCustomer(Integer customerNumber);
    // Get just the credit limit of a customer
    BigDecimal getCreditLimit(Integer customerNumber);

    // Update just the credit limit of a customer
    CustomerResponseDTO updateCreditLimit(Integer customerNumber, BigDecimal creditLimit);

    // Search customers by country and city
    List<CustomerResponseDTO> searchByCountryAndCity(String country, String city);

}