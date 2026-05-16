package com.classicmodels.repository;

import com.classicmodels.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // This allows searching customers by both country AND city
    List<Customer> findByCountryAndCity(String country, String city);

}