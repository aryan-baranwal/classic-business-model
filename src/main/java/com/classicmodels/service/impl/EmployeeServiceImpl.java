package com.classicmodels.service.impl;

import com.classicmodels.entity.Employee;
import com.classicmodels.exception.EmployeeNotFoundException;
import com.classicmodels.repository.EmployeeRepository;
import com.classicmodels.service.EmployeeService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl
        implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository
    ) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(
            Integer employeeNumber
    ) {

        return employeeRepository
                .findById(employeeNumber)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id : "
                                        + employeeNumber
                        )
                );
    }

    @Override
    public Employee createEmployee(
            Employee employee
    ) {

        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(
            Integer employeeNumber,
            Employee employee
    ) {

        Employee existingEmployee =
                getEmployeeById(employeeNumber);

        existingEmployee.setFirstName(
                employee.getFirstName()
        );

        existingEmployee.setLastName(
                employee.getLastName()
        );

        existingEmployee.setExtension(
                employee.getExtension()
        );

        existingEmployee.setEmail(
                employee.getEmail()
        );

        existingEmployee.setJobTitle(
                employee.getJobTitle()
        );

        existingEmployee.setOffice(
                employee.getOffice()
        );

        existingEmployee.setManager(
                employee.getManager()
        );

        return employeeRepository.save(
                existingEmployee
        );
    }

    @Override
    public String deleteEmployee(
            Integer employeeNumber
    ) {

        Employee employee =
                getEmployeeById(employeeNumber);

        employeeRepository.delete(employee);

        return "Employee deleted successfully";
    }

    @Override
    public Employee getManager(
            Integer employeeNumber
    ) {

        Employee employee =
                getEmployeeById(employeeNumber);

        return employee.getManager();
    }

    @Override
    public List<Employee> getSubordinates(
            Integer employeeNumber
    ) {

        return employeeRepository
                .findByManagerEmployeeNumber(
                        employeeNumber
                );
    }
}