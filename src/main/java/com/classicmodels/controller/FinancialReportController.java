package com.classicmodels.controller;

import com.classicmodels.dto.HighRiskCustomerDto;
import com.classicmodels.dto.MonthlyRevenueDto;
import com.classicmodels.service.ReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * Separate REST Controller for:
 * - Monthly Revenue Report
 * - High Risk Customers Report
 */
@RestController
@RequestMapping("/api/reports")
public class FinancialReportController {

    private final ReportService reportService;

    public FinancialReportController(
            ReportService reportService
    ) {
        this.reportService = reportService;
    }

    // MONTHLY REVENUE REPORT
    // GET /api/reports/monthly-revenue

    @GetMapping("/monthly-revenue")
    public List<MonthlyRevenueDto>
    getMonthlyRevenue() {

        return reportService
                .getMonthlyRevenue();
    }

    // HIGH RISK CUSTOMERS REPORT
    // GET /api/reports/high-risk-customers

    @GetMapping("/high-risk-customers")
    public List<HighRiskCustomerDto>
    getHighRiskCustomers() {

        return reportService
                .getHighRiskCustomers();
    }
}