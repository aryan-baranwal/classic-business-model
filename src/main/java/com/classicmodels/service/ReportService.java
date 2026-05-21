package com.classicmodels.service;

import com.classicmodels.dto.*;

import java.util.List;

public interface ReportService {

    List<SalesByEmployeeDto> getSalesByEmployee();

    List<SalesByCountryDto> getSalesByCountry();

    List<CustomerExposureDto> getCustomerExposure();

    OrderValueDto getOrderValue(
            Integer orderNumber
    );

    // MONTHLY REVENUE REPORT
    List<MonthlyRevenueDto> getMonthlyRevenue();

    // HIGH RISK CUSTOMERS REPORT
    List<HighRiskCustomerDto> getHighRiskCustomers();
}
