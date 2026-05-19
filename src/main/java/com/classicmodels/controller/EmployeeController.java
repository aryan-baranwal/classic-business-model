package com.classicmodels.controller;

import com.classicmodels.dto.DeleteEmployeeResponseDto;
import com.classicmodels.dto.EmployeeRequestDto;
import com.classicmodels.dto.EmployeeResponseDto;
import com.classicmodels.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(
        name = "Employee Controller",
        description = "REST APIs for Employee Module"
)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(
            EmployeeService employeeService
    ) {
        this.employeeService = employeeService;
    }

    // GET ALL EMPLOYEES

    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>>
    getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees()
        );
    }

    // GET EMPLOYEE BY ID

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{employeeNumber}")
    public ResponseEntity<EmployeeResponseDto>
    getEmployeeById(
            @PathVariable Integer employeeNumber
    ) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(
                        employeeNumber
                )
        );
    }

    // CREATE EMPLOYEE

    @Operation(summary = "Create new employee")
    @PostMapping
    public ResponseEntity<EmployeeResponseDto>
    createEmployee(
            @Valid @RequestBody EmployeeRequestDto dto
    ) {

        return new ResponseEntity<>(
                employeeService.createEmployee(dto),
                HttpStatus.CREATED
        );
    }

    // UPDATE EMPLOYEE

    @Operation(summary = "Update employee")
    @PutMapping("/{employeeNumber}")
    public ResponseEntity<EmployeeResponseDto>
    updateEmployee(
            @PathVariable Integer employeeNumber,
            @Valid @RequestBody EmployeeRequestDto dto
    ) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(
                        employeeNumber,
                        dto
                )
        );
    }

    // DELETE EMPLOYEE

    @Operation(summary = "Delete employee")
    @DeleteMapping("/{employeeNumber}")
    public ResponseEntity<DeleteEmployeeResponseDto>
    deleteEmployee(
            @PathVariable Integer employeeNumber
    ) {

        return ResponseEntity.ok(
                employeeService.deleteEmployee(
                        employeeNumber
                )
        );
    }

    // GET MANAGER

    @Operation(summary = "Get employee manager")
    @GetMapping("/{employeeNumber}/manager")
    public ResponseEntity<EmployeeResponseDto>
    getManager(
            @PathVariable Integer employeeNumber
    ) {

        return ResponseEntity.ok(
                employeeService.getManager(
                        employeeNumber
                )
        );
    }

    // GET SUBORDINATES

    @Operation(summary = "Get employee subordinates")
    @GetMapping("/{employeeNumber}/subordinates")
    public ResponseEntity<List<EmployeeResponseDto>>
    getSubordinates(
            @PathVariable Integer employeeNumber
    ) {

        return ResponseEntity.ok(
                employeeService.getSubordinates(
                        employeeNumber
                )
        );
    }
}