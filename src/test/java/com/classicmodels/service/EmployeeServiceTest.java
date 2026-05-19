package com.classicmodels.service;

import com.classicmodels.dto.DeleteEmployeeResponseDto;
import com.classicmodels.dto.EmployeeRequestDto;
import com.classicmodels.dto.EmployeeResponseDto;
import com.classicmodels.entity.Employee;
import com.classicmodels.entity.Office;
import com.classicmodels.exception.EmployeeNotFoundException;
import com.classicmodels.repository.EmployeeRepository;
import com.classicmodels.repository.OfficeRepository;
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

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    private Office office;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        office = new Office();
        office.setOfficeCode("1");

        employee = new Employee();

        employee.setEmployeeNumber(1002);
        employee.setFirstName("Diane");
        employee.setLastName("Murphy");
        employee.setExtension("x5800");
        employee.setEmail("dmurphy@classicmodelcars.com");
        employee.setJobTitle("President");
        employee.setOffice(office);
    }

    // TEST GET ALL EMPLOYEES

    @Test
    void testGetAllEmployees() {

        List<Employee> employeeList =
                List.of(employee);

        when(employeeRepository.findAll())
                .thenReturn(employeeList);

        List<EmployeeResponseDto> result =
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

        EmployeeResponseDto result =
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

        EmployeeRequestDto dto =
                new EmployeeRequestDto();

        dto.setEmployeeNumber(1002);
        dto.setFirstName("Diane");
        dto.setLastName("Murphy");
        dto.setExtension("x5800");
        dto.setEmail("dmurphy@classicmodelcars.com");
        dto.setJobTitle("President");
        dto.setOfficeCode("1");

        when(officeRepository.findById("1"))
                .thenReturn(Optional.of(office));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponseDto savedEmployee =
                employeeService.createEmployee(dto);

        assertNotNull(savedEmployee);

        assertEquals(
                employee.getEmployeeNumber(),
                savedEmployee.getEmployeeNumber()
        );
    }

    // TEST UPDATE EMPLOYEE

    @Test
    void testUpdateEmployee() {

        EmployeeRequestDto dto =
                new EmployeeRequestDto();

        dto.setFirstName("Updated");

        dto.setLastName("Employee");

        dto.setExtension("x1111");

        dto.setEmail("updated@classicmodelcars.com");

        dto.setJobTitle("Manager");

        dto.setOfficeCode("1");

        when(employeeRepository.findById(1002))
                .thenReturn(Optional.of(employee));

        when(officeRepository.findById("1"))
                .thenReturn(Optional.of(office));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponseDto result =
                employeeService.updateEmployee(
                        1002,
                        dto
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

        DeleteEmployeeResponseDto result =
                employeeService.deleteEmployee(1002);

        assertEquals(
                "Employee deleted successfully",
                result.getMessage()
        );

        assertEquals(
                1002,
                result.getEmployee().getEmployeeNumber()
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

        manager.setOffice(office);

        employee.setManager(manager);

        when(employeeRepository.findById(1002))
                .thenReturn(Optional.of(employee));

        EmployeeResponseDto result =
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

        List<EmployeeResponseDto> result =
                employeeService.getSubordinates(1001);

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );
    }
}