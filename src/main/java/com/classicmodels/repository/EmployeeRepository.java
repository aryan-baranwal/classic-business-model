package com.classicmodels.repository;

import com.classicmodels.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByJobTitle(String jobTitle);

    List<Employee> findByManagerEmployeeNumber(
            Integer employeeNumber
    );
}