package com.classicmodels.service;

import com.classicmodels.dto.CustomerExposureDto;
import com.classicmodels.dto.OrderValueDto;
import com.classicmodels.dto.SalesByCountryDto;
import com.classicmodels.dto.SalesByEmployeeDto;

import java.util.List;

public interface ReportService {

    List<SalesByEmployeeDto> getSalesByEmployee();

    List<SalesByCountryDto> getSalesByCountry();

    List<CustomerExposureDto> getCustomerExposure();

    OrderValueDto getOrderValue(
            Integer orderNumber
    );
}