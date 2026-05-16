package com.classicmodels.service;

import com.classicmodels.dto.OfficeResponseDto;

import java.util.List;

public interface OfficeService {

    List<OfficeResponseDto> getAllOffices();

    OfficeResponseDto getOfficeByCode(String officeCode);
}