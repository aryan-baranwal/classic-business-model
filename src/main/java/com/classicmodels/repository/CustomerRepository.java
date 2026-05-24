package com.classicmodels.repository;

import com.classicmodels.dto.HighRiskCustomerDto;
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
           SUM(od.quantityOrdered * od.priceEach),
           MIN(o.orderDate),
           MAX(o.orderDate)
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
           SUM(od.quantityOrdered * od.priceEach),
           MIN(o.orderDate),
           MAX(o.orderDate)
           FROM Customer c
           JOIN c.orders o
           JOIN o.orderDetails od
           GROUP BY c.customerNumber,
                    c.customerName,
                    c.creditLimit
           """)
    List<Object[]> getCustomerExposure();

    // HIGH RISK CUSTOMERS REPORT

    @Query("""
       SELECT new com.classicmodels.dto.HighRiskCustomerDto(
           c.customerNumber,
           c.customerName,
           c.creditLimit,
           COALESCE(SUM(p.amount), 0),
           c.creditLimit -
           COALESCE(SUM(p.amount), 0),
           MIN(p.paymentDate),
           MAX(p.paymentDate)
       )
       FROM Customer c
       LEFT JOIN c.payments p
       WHERE c.creditLimit IS NOT NULL
       GROUP BY
           c.customerNumber,
           c.customerName,
           c.creditLimit
       HAVING
           (c.creditLimit -
           COALESCE(SUM(p.amount), 0))
           <=
           (c.creditLimit * 0.2)
       ORDER BY
           (c.creditLimit -
           COALESCE(SUM(p.amount), 0))
       """)
    List<HighRiskCustomerDto>
    findHighRiskCustomers();
}
