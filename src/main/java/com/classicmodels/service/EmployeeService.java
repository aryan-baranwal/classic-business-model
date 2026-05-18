package com.classicmodels.service;

import com.classicmodels.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer employeeNumber);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(
            Integer employeeNumber,
            Employee employee
    );

    String deleteEmployee(Integer employeeNumber);

    Employee getManager(Integer employeeNumber);

    List<Employee> getSubordinates(Integer employeeNumber);
}