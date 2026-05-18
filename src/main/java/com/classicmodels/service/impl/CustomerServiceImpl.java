package com.classicmodels.service.impl;

import com.classicmodels.dto.CustomerRequestDTO;
import com.classicmodels.dto.CustomerResponseDTO;
import com.classicmodels.entity.Customer;
import com.classicmodels.exception.CustomerNotFoundException;
import com.classicmodels.repository.CustomerRepository;
import com.classicmodels.service.CustomerService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    // Constructor injection
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Convert Entity → ResponseDTO
    private CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getCustomerNumber(),
                customer.getCustomerName(),
                customer.getContactLastName(),
                customer.getContactFirstName(),
                customer.getPhone(),
                customer.getAddressLine1(),
                customer.getAddressLine2(),
                customer.getCity(),
                customer.getState(),
                customer.getPostalCode(),
                customer.getCountry(),
                customer.getCreditLimit()
        );
    }

    // Convert RequestDTO → Entity
    private Customer toEntity(CustomerRequestDTO request) {
        Customer customer = new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setContactLastName(request.getContactLastName());
        customer.setContactFirstName(request.getContactFirstName());
        customer.setPhone(request.getPhone());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPostalCode(request.getPostalCode());
        customer.setCountry(request.getCountry());
        customer.setCreditLimit(request.getCreditLimit());
        return customer;
    }

    // Create a new customer
    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
        Customer customer = toEntity(request);
        return toResponseDTO(customerRepository.save(customer));
    }

    // Get all customers
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Get one customer by ID
    @Override
    public CustomerResponseDTO getCustomerById(Integer customerNumber) {
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException(customerNumber));
        return toResponseDTO(customer);
    }

    // Update customer profile
    @Override
    public CustomerResponseDTO updateCustomer(Integer customerNumber, CustomerRequestDTO request) {
        Customer existing = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException(customerNumber));
        existing.setCustomerName(request.getCustomerName());
        existing.setContactFirstName(request.getContactFirstName());
        existing.setContactLastName(request.getContactLastName());
        existing.setPhone(request.getPhone());
        existing.setAddressLine1(request.getAddressLine1());
        existing.setAddressLine2(request.getAddressLine2());
        existing.setCity(request.getCity());
        existing.setState(request.getState());
        existing.setPostalCode(request.getPostalCode());
        existing.setCountry(request.getCountry());
        existing.setCreditLimit(request.getCreditLimit());
        return toResponseDTO(customerRepository.save(existing));
    }

    // Delete a customer
    @Override
    public CustomerResponseDTO deleteCustomer(Integer customerNumber) {
        Customer existing = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException(customerNumber));
        CustomerResponseDTO deletedCustomer = toResponseDTO(existing);
        customerRepository.delete(existing);
        return deletedCustomer;
    }

    // Get just the credit limit
    @Override
    public BigDecimal getCreditLimit(Integer customerNumber) {
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException(customerNumber));
        return customer.getCreditLimit();
    }

    // Update just the credit limit
    @Override
    public CustomerResponseDTO updateCreditLimit(Integer customerNumber, BigDecimal creditLimit) {
        Customer existing = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException(customerNumber));
        existing.setCreditLimit(creditLimit);
        return toResponseDTO(customerRepository.save(existing));
    }

    // Search by country and city
    @Override
    public List<CustomerResponseDTO> searchByCountryAndCity(String country, String city) {
        return customerRepository.findByCountryAndCity(country, city)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}