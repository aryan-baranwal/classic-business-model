package com.classicmodels.repository;

import com.classicmodels.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {

    // FIND EMPLOYEE BY EMAIL

    Optional<Employee> findByEmail(String email);

    // FIND EMPLOYEES BY JOB TITLE

    List<Employee> findByJobTitle(String jobTitle);

    // FIND SUBORDINATES UNDER A MANAGER

    List<Employee> findByManagerEmployeeNumber(
            Integer employeeNumber
    );

    // CHECK EMAIL EXISTS

    boolean existsByEmail(String email);
}