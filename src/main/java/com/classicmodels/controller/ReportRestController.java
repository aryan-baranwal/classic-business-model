package com.classicmodels.controller;

import com.classicmodels.dto.CustomerExposureDto;
import com.classicmodels.dto.OrderValueDto;
import com.classicmodels.dto.SalesByCountryDto;
import com.classicmodels.dto.SalesByEmployeeDto;

import com.classicmodels.service.ReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService reportService;

    public ReportRestController(
            ReportService reportService
    ) {
        this.reportService = reportService;
    }

    // SALES BY EMPLOYEE REPORT

    @GetMapping("/sales-by-employee")
    public List<SalesByEmployeeDto>
    getSalesByEmployee() {

        return reportService
                .getSalesByEmployee();
    }

    // SALES BY COUNTRY REPORT

    @GetMapping("/sales-by-country")
    public List<SalesByCountryDto>
    getSalesByCountry() {

        return reportService
                .getSalesByCountry();
    }

    // CUSTOMER EXPOSURE REPORT

    @GetMapping("/customer-exposure")
    public List<CustomerExposureDto>
    getCustomerExposure() {

        return reportService
                .getCustomerExposure();
    }

    // ORDER VALUE REPORT

    @GetMapping("/order-value/{orderNumber}")
    public OrderValueDto getOrderValue(
            @PathVariable Integer orderNumber
    ) {

        return reportService
                .getOrderValue(orderNumber);
    }
}