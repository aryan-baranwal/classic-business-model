package com.classicmodels.repository;

import com.classicmodels.entity.Employee;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(
        replace =
                AutoConfigureTestDatabase.Replace.NONE
)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    // TEST FIND ALL EMPLOYEES

    @Test
    void testFindAllEmployees() {

        List<Employee> employees =
                employeeRepository.findAll();

        assertNotNull(employees);

        assertFalse(employees.isEmpty());
    }

    // TEST FIND EMPLOYEE BY ID

    @Test
    void testFindEmployeeById() {

        Optional<Employee> employee =
                employeeRepository.findById(1002);

        assertTrue(employee.isPresent());

        assertEquals(
                "Diane",
                employee.get().getFirstName()
        );
    }

    // TEST FIND BY EMAIL

    @Test
    void testFindByEmail() {

        Optional<Employee> employee =
                employeeRepository.findByEmail(
                        "dmurphy@classicmodelcars.com"
                );

        assertTrue(employee.isPresent());

        assertEquals(
                "President",
                employee.get().getJobTitle()
        );
    }

    // TEST FIND BY JOB TITLE

    @Test
    void testFindByJobTitle() {

        List<Employee> employees =
                employeeRepository.findByJobTitle(
                        "Sales Rep"
                );

        assertNotNull(employees);

        assertFalse(employees.isEmpty());
    }

    // TEST EXISTS BY EMAIL

    @Test
    void testExistsByEmail() {

        boolean exists =
                employeeRepository.existsByEmail(
                        "dmurphy@classicmodelcars.com"
                );

        assertTrue(exists);
    }

    // NEGATIVE TEST CASE

    @Test
    void testFindByEmailNotFound() {

        Optional<Employee> employee =
                employeeRepository.findByEmail(
                        "invalid@gmail.com"
                );

        assertFalse(employee.isPresent());
    }
}