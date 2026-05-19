package com.classicmodels.service;

import com.classicmodels.dto.OfficeResponseDto;
import com.classicmodels.entity.Employee;
import java.util.List;

public interface OfficeService {

    List<OfficeResponseDto> getAllOffices();

    OfficeResponseDto getOfficeByCode(String officeCode);

    List<Employee> getEmployeesByOffice(String officeCode);
}