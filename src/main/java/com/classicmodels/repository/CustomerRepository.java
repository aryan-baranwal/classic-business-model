package com.classicmodels.repository;

import com.classicmodels.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    // FIND CUSTOMER BY CUSTOMER NAME

    Optional<Customer> findByCustomerName(
            String customerName
    );

    // FIND CUSTOMERS BY COUNTRY

    List<Customer> findByCountry(String country);

    // FIND CUSTOMERS BY COUNTRY AND CITY

    List<Customer> findByCountryAndCity(
            String country,
            String city
    );

    // CHECK CUSTOMER EXISTS BY PHONE

    boolean existsByPhone(String phone);

    // SALES BY COUNTRY REPORT

    @Query("""
           SELECT
           c.country,
           SUM(od.quantityOrdered * od.priceEach)
           FROM Customer c
           JOIN c.orders o
           JOIN o.orderDetails od
           GROUP BY c.country
           """)
    List<Object[]> getSalesByCountry();

    // CUSTOMER EXPOSURE REPORT

    @Query("""
           SELECT
           c.customerName,
           c.creditLimit,
           SUM(od.quantityOrdered * od.priceEach)
           FROM Customer c
           JOIN c.orders o
           JOIN o.orderDetails od
           GROUP BY c.customerNumber,
                    c.customerName,
                    c.creditLimit
           """)
    List<Object[]> getCustomerExposure();
}