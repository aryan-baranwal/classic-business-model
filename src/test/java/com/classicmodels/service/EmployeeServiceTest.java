package com.classicmodels.service;

import com.classicmodels.entity.Employee;
import com.classicmodels.exception.EmployeeNotFoundException;
import com.classicmodels.repository.EmployeeRepository;
import com.classicmodels.service.impl.EmployeeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        employee = new Employee();

        employee.setEmployeeNumber(1002);
        employee.setFirstName("Diane");
        employee.setLastName("Murphy");
        employee.setExtension("x5800");
        employee.setEmail("dmurphy@classicmodelcars.com");
        employee.setJobTitle("President");
    }

    // TEST GET ALL EMPLOYEES

    @Test
    void testGetAllEmployees() {

        List<Employee> employeeList =
                List.of(employee);

        when(employeeRepository.findAll())
                .thenReturn(employeeList);

        List<Employee> result =
                employeeService.getAllEmployees();

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );

        verify(employeeRepository,
                times(1))
                .findAll();
    }

    // TEST GET EMPLOYEE BY ID

    @Test
    void testGetEmployeeById() {

        when(employeeRepository.findById(1002))
                .thenReturn(Optional.of(employee));

        Employee result =
                employeeService.getEmployeeById(1002);

        assertNotNull(result);

        assertEquals(
                "Diane",
                result.getFirstName()
        );
    }

    // NEGATIVE TEST CASE

    @Test
    void testGetEmployeeByIdNotFound() {

        when(employeeRepository.findById(9999))
                .thenReturn(Optional.empty());

        EmployeeNotFoundException exception =
                assertThrows(
                        EmployeeNotFoundException.class,
                        () -> employeeService.getEmployeeById(9999)
                );

        assertEquals(
                "Employee not found with id : 9999",
                exception.getMessage()
        );
    }

    // TEST CREATE EMPLOYEE

    @Test
    void testCreateEmployee() {

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        Employee savedEmployee =
                employeeService.createEmployee(employee);

        assertNotNull(savedEmployee);

        assertEquals(
                employee.getEmployeeNumber(),
                savedEmployee.getEmployeeNumber()
        );
    }

    // TEST UPDATE EMPLOYEE

    @Test
    void testUpdateEmployee() {

        Employee updatedEmployee =
                new Employee();

        updatedEmployee.setFirstName("Updated");

        updatedEmployee.setLastName("Employee");

        updatedEmployee.setExtension("x1111");

        updatedEmployee.setEmail("updated@classicmodelcars.com");

        updatedEmployee.setJobTitle("Manager");

        when(employeeRepository.findById(1002))
                .thenReturn(Optional.of(employee));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(updatedEmployee);

        Employee result =
                employeeService.updateEmployee(
                        1002,
                        updatedEmployee
                );

        assertNotNull(result);

        assertEquals(
                "Updated",
                result.getFirstName()
        );
    }

    // TEST DELETE EMPLOYEE

    @Test
    void testDeleteEmployee() {

        when(employeeRepository.findById(1002))
                .thenReturn(Optional.of(employee));

        doNothing()
                .when(employeeRepository)
                .delete(employee);

        String result =
                employeeService.deleteEmployee(1002);

        assertEquals(
                "Employee deleted successfully",
                result
        );

        verify(employeeRepository,
                times(1))
                .delete(employee);
    }

    // TEST GET MANAGER

    @Test
    void testGetManager() {

        Employee manager =
                new Employee();

        manager.setEmployeeNumber(1001);

        manager.setFirstName("Manager");

        employee.setManager(manager);

        when(employeeRepository.findById(1002))
                .thenReturn(Optional.of(employee));

        Employee result =
                employeeService.getManager(1002);

        assertNotNull(result);

        assertEquals(
                "Manager",
                result.getFirstName()
        );
    }

    // TEST GET SUBORDINATES

    @Test
    void testGetSubordinates() {

        List<Employee> subordinates =
                new ArrayList<>();

        subordinates.add(employee);

        when(employeeRepository
                .findByManagerEmployeeNumber(1001))
                .thenReturn(subordinates);

        List<Employee> result =
                employeeService.getSubordinates(1001);

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );
    }
}