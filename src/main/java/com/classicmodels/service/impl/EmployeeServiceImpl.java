package com.classicmodels.service.impl;

import com.classicmodels.dto.DeleteEmployeeResponseDto;
import com.classicmodels.dto.EmployeeRequestDto;
import com.classicmodels.dto.EmployeeResponseDto;
import com.classicmodels.entity.Employee;
import com.classicmodels.entity.Office;
import com.classicmodels.exception.EmployeeNotFoundException;
import com.classicmodels.repository.EmployeeRepository;
import com.classicmodels.repository.OfficeRepository;
import com.classicmodels.service.EmployeeService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl
        implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            OfficeRepository officeRepository
    ) {

        this.employeeRepository = employeeRepository;
        this.officeRepository = officeRepository;
    }

    // GET ALL EMPLOYEES

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {

        return employeeRepository
                .findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // GET EMPLOYEE BY ID

    @Override
    public EmployeeResponseDto getEmployeeById(
            Integer employeeNumber
    ) {

        Employee employee =
                employeeRepository
                        .findById(employeeNumber)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found with id : "
                                                + employeeNumber
                                )
                        );

        return mapToResponseDto(employee);
    }

    // CREATE EMPLOYEE

    @Override
    public EmployeeResponseDto createEmployee(
            EmployeeRequestDto dto
    ) {

        Office office =
                officeRepository
                        .findById(dto.getOfficeCode())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Office not found"
                                )
                        );

        Employee manager = null;

        if (dto.getManagerId() != null) {

            manager =
                    employeeRepository
                            .findById(dto.getManagerId())
                            .orElseThrow(() ->
                                    new EmployeeNotFoundException(
                                            "Manager not found"
                                    )
                            );
        }

        Employee employee = new Employee();

        employee.setEmployeeNumber(
                dto.getEmployeeNumber()
        );

        employee.setFirstName(
                dto.getFirstName()
        );

        employee.setLastName(
                dto.getLastName()
        );

        employee.setExtension(
                dto.getExtension()
        );

        employee.setEmail(
                dto.getEmail()
        );

        employee.setJobTitle(
                dto.getJobTitle()
        );

        employee.setOffice(office);

        employee.setManager(manager);

        Employee savedEmployee =
                employeeRepository.save(employee);

        return mapToResponseDto(savedEmployee);
    }

    // UPDATE EMPLOYEE

    @Override
    public EmployeeResponseDto updateEmployee(
            Integer employeeNumber,
            EmployeeRequestDto dto
    ) {

        Employee existingEmployee =
                employeeRepository
                        .findById(employeeNumber)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found with id : "
                                                + employeeNumber
                                )
                        );

        Office office =
                officeRepository
                        .findById(dto.getOfficeCode())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Office not found"
                                )
                        );

        Employee manager = null;

        if (dto.getManagerId() != null) {

            manager =
                    employeeRepository
                            .findById(dto.getManagerId())
                            .orElseThrow(() ->
                                    new EmployeeNotFoundException(
                                            "Manager not found"
                                    )
                            );
        }

        existingEmployee.setFirstName(
                dto.getFirstName()
        );

        existingEmployee.setLastName(
                dto.getLastName()
        );

        existingEmployee.setExtension(
                dto.getExtension()
        );

        existingEmployee.setEmail(
                dto.getEmail()
        );

        existingEmployee.setJobTitle(
                dto.getJobTitle()
        );

        existingEmployee.setOffice(
                office
        );

        existingEmployee.setManager(
                manager
        );

        Employee updatedEmployee =
                employeeRepository.save(existingEmployee);

        return mapToResponseDto(updatedEmployee);
    }

    // DELETE EMPLOYEE

    @Override
    public DeleteEmployeeResponseDto deleteEmployee(
            Integer employeeNumber
    ) {

        Employee employee =
                employeeRepository
                        .findById(employeeNumber)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found with id : "
                                                + employeeNumber
                                )
                        );

        EmployeeResponseDto employeeDto =
                mapToResponseDto(employee);

        employeeRepository.delete(employee);

        return new DeleteEmployeeResponseDto(
                "Employee deleted successfully",
                employeeDto
        );
    }

    // GET MANAGER

    @Override
    public EmployeeResponseDto getManager(
            Integer employeeNumber
    ) {

        Employee employee =
                employeeRepository
                        .findById(employeeNumber)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found with id : "
                                                + employeeNumber
                                )
                        );

        if (employee.getManager() == null) {

            throw new RuntimeException(
                    "Manager not found"
            );
        }

        return mapToResponseDto(
                employee.getManager()
        );
    }

    // GET SUBORDINATES

    @Override
    public List<EmployeeResponseDto> getSubordinates(
            Integer employeeNumber
    ) {

        return employeeRepository
                .findByManagerEmployeeNumber(
                        employeeNumber
                )
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // DTO MAPPING METHOD

    private EmployeeResponseDto mapToResponseDto(
            Employee employee
    ) {

        EmployeeResponseDto dto =
                new EmployeeResponseDto();

        dto.setEmployeeNumber(
                employee.getEmployeeNumber()
        );

        dto.setFirstName(
                employee.getFirstName()
        );

        dto.setLastName(
                employee.getLastName()
        );

        dto.setEmail(
                employee.getEmail()
        );

        dto.setJobTitle(
                employee.getJobTitle()
        );

        if (employee.getOffice() != null) {

            dto.setOfficeCode(
                    employee.getOffice().getOfficeCode()
            );
        }

        return dto;
    }
}