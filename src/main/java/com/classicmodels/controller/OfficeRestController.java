package com.classicmodels.controller;

import com.classicmodels.dto.OfficeResponseDto;
import com.classicmodels.entity.Employee;
import com.classicmodels.service.OfficeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offices")
public class OfficeRestController {

    private final OfficeService officeService;

    public OfficeRestController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public List<OfficeResponseDto> getAllOffices() {

        return officeService.getAllOffices();
    }

    @GetMapping("/{officeCode}")
    public OfficeResponseDto getOfficeByCode(
            @PathVariable String officeCode) {

        return officeService.getOfficeByCode(officeCode);
    }

    /*@GetMapping("/{officeCode}/employees")
    public List<Employee> getEmployeesByOffice(
            @PathVariable String officeCode) {

        return officeService.getEmployeesByOffice(officeCode);
    }*/
}