package com.classicmodels.service.impl;

import com.classicmodels.dto.*;

import com.classicmodels.entity.Order;
import com.classicmodels.exception.OrderNotFoundException;
import com.classicmodels.repository.CustomerRepository;
import com.classicmodels.repository.EmployeeRepository;
import com.classicmodels.repository.OrderRepository;
import com.classicmodels.repository.PaymentRepository;
import com.classicmodels.service.ReportService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

            dto.setReportStartDate(
                    (LocalDate) row[2]
            );

            dto.setReportEndDate(
                    (LocalDate) row[3]
            );

            dto.setDateRange(
                    formatDateRange(
                            dto.getReportStartDate(),
                            dto.getReportEndDate()
                    )
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

            dto.setReportStartDate(
                    (LocalDate) row[2]
            );

            dto.setReportEndDate(
                    (LocalDate) row[3]
            );

            dto.setDateRange(
                    formatDateRange(
                            dto.getReportStartDate(),
                            dto.getReportEndDate()
                    )
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

            dto.setReportStartDate(
                    (LocalDate) row[3]
            );

            dto.setReportEndDate(
                    (LocalDate) row[4]
            );

            dto.setDateRange(
                    formatDateRange(
                            dto.getReportStartDate(),
                            dto.getReportEndDate()
                    )
            );

            response.add(dto);
        }

        return response;
    }

    @Override
    public OrderValueDto getOrderValue(
            Integer orderNumber
    ) {

        Order order = orderRepository
                .findById(orderNumber)
                .orElseThrow(() ->
                        new OrderNotFoundException(orderNumber)
                );

        List<Object[]> results =
                orderRepository
                        .getOrderValue(orderNumber);

        if (results.isEmpty()) {
            OrderValueDto dto =
                    new OrderValueDto();

            dto.setOrderNumber(
                    order.getOrderNumber()
            );

            dto.setTotalOrderValue(
                    0.0
            );

            dto.setOrderDate(
                    order.getOrderDate()
            );

            dto.setRequiredDate(
                    order.getRequiredDate()
            );

            dto.setShippedDate(
                    order.getShippedDate()
            );

            dto.setDateRange(
                    formatDateRange(
                            dto.getOrderDate(),
                            dto.getRequiredDate()
                    )
            );

            return dto;
        }

        Object[] result = results.get(0);

        OrderValueDto dto =
                new OrderValueDto();

        dto.setOrderNumber(
                (Integer) result[0]
        );

        dto.setTotalOrderValue(
                ((Number) result[1]).doubleValue()
        );

        dto.setOrderDate(
                (LocalDate) result[2]
        );

        dto.setRequiredDate(
                (LocalDate) result[3]
        );

        dto.setShippedDate(
                (LocalDate) result[4]
        );

        dto.setDateRange(
                formatDateRange(
                        dto.getOrderDate(),
                        dto.getRequiredDate()
                )
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

            dto.setMonthStartDate(
                    (LocalDate) row[2]
            );

            dto.setMonthEndDate(
                    (LocalDate) row[3]
            );

            dto.setDateRange(
                    formatDateRange(
                            dto.getMonthStartDate(),
                            dto.getMonthEndDate()
                    )
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

    private String formatDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate == null && endDate == null) {
            return "No date recorded";
        }
        if (startDate != null && startDate.equals(endDate)) {
            return startDate.toString();
        }
        return startDate + " to " + endDate;
    }
}
