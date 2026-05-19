package com.classicmodels.service;

import com.classicmodels.dto.DeleteEmployeeResponseDto;
import com.classicmodels.dto.EmployeeRequestDto;
import com.classicmodels.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {

    // GET ALL EMPLOYEES

    List<EmployeeResponseDto> getAllEmployees();

    // GET EMPLOYEE BY ID

    EmployeeResponseDto getEmployeeById(
            Integer employeeNumber
    );

    // CREATE EMPLOYEE

    EmployeeResponseDto createEmployee(
            EmployeeRequestDto dto
    );

    // UPDATE EMPLOYEE

    EmployeeResponseDto updateEmployee(
            Integer employeeNumber,
            EmployeeRequestDto dto
    );

    // DELETE EMPLOYEE

    DeleteEmployeeResponseDto deleteEmployee(
            Integer employeeNumber
    );

    // GET MANAGER

    EmployeeResponseDto getManager(
            Integer employeeNumber
    );

    // GET SUBORDINATES

    List<EmployeeResponseDto> getSubordinates(
            Integer employeeNumber
    );
}