package com.classicmodels.service;

import com.classicmodels.entity.Office;

import java.util.List;

public interface OfficeService {

    List<Office> getAllOffices();

    Office getOfficeByCode(String officeCode);
}