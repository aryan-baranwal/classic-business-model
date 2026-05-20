package com.classicmodels.service.impl;

import com.classicmodels.dto.*;

import com.classicmodels.repository.CustomerRepository;
import com.classicmodels.repository.EmployeeRepository;
import com.classicmodels.repository.OrderRepository;
import com.classicmodels.repository.PaymentRepository;
import com.classicmodels.service.ReportService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    public ReportServiceImpl(
            EmployeeRepository employeeRepository,
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            PaymentRepository paymentRepository
    ) {

        this.employeeRepository = employeeRepository;

        this.customerRepository = customerRepository;

        this.orderRepository = orderRepository;

        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<SalesByEmployeeDto>
    getSalesByEmployee() {

        List<Object[]> results =
                employeeRepository
                        .getSalesByEmployee();

        List<SalesByEmployeeDto> response =
                new ArrayList<>();

        for (Object[] row : results) {

            SalesByEmployeeDto dto =
                    new SalesByEmployeeDto();

            dto.setEmployeeName(
                    (String) row[0]
            );

            dto.setTotalSales(
                    ((Number) row[1]).doubleValue()
            );

            response.add(dto);
        }

        return response;
    }

    @Override
    public List<SalesByCountryDto>
    getSalesByCountry() {

        List<Object[]> results =
                customerRepository
                        .getSalesByCountry();

        List<SalesByCountryDto> response =
                new ArrayList<>();

        for (Object[] row : results) {

            SalesByCountryDto dto =
                    new SalesByCountryDto();

            dto.setCountry(
                    (String) row[0]
            );

            dto.setTotalSales(
                    ((Number) row[1]).doubleValue()
            );

            response.add(dto);
        }

        return response;
    }

    @Override
    public List<CustomerExposureDto>
    getCustomerExposure() {

        List<Object[]> results =
                customerRepository
                        .getCustomerExposure();

        List<CustomerExposureDto> response =
                new ArrayList<>();

        for (Object[] row : results) {

            CustomerExposureDto dto =
                    new CustomerExposureDto();

            Double creditLimit =
                    ((Number) row[1]).doubleValue();

            Double totalOrders =
                    ((Number) row[2]).doubleValue();

            dto.setCustomerName(
                    (String) row[0]
            );

            dto.setCreditLimit(
                    creditLimit
            );

            dto.setTotalOrders(
                    totalOrders
            );

            dto.setExposure(
                    totalOrders - creditLimit
            );

            response.add(dto);
        }

        return response;
    }

    @Override
    public OrderValueDto getOrderValue(
            Integer orderNumber
    ) {

        List<Object[]> results =
                orderRepository
                        .getOrderValue(orderNumber);

        Object[] result = results.get(0);

        OrderValueDto dto =
                new OrderValueDto();

        dto.setOrderNumber(
                (Integer) result[0]
        );

        dto.setTotalOrderValue(
                ((Number) result[1]).doubleValue()
        );

        return dto;
    }
// MONTHLY REVENUE REPORT

    @Override
    public List<MonthlyRevenueDto>
    getMonthlyRevenue() {

        List<Object[]> results =
                paymentRepository
                        .findMonthlyRevenue();

        List<MonthlyRevenueDto> response =
                new ArrayList<>();

        for (Object[] row : results) {

            MonthlyRevenueDto dto =
                    new MonthlyRevenueDto();

            dto.setMonth(
                    (String) row[0]
            );

            dto.setTotalRevenue(
                    (java.math.BigDecimal) row[1]
            );

            response.add(dto);
        }

        return response;
    }

// HIGH RISK CUSTOMERS REPORT

    @Override
    public List<HighRiskCustomerDto>
    getHighRiskCustomers() {

        return customerRepository
                .findHighRiskCustomers();
    }
}