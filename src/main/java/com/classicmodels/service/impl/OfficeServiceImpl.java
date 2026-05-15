package com.classicmodels.service.impl;

import com.classicmodels.entity.Office;
import com.classicmodels.repository.OfficeRepository;
import com.classicmodels.service.OfficeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeServiceImpl(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    @Override
    public Office getOfficeByCode(String officeCode) {

        return officeRepository.findById(officeCode)
                .orElseThrow(() ->
                        new RuntimeException("Office not found"));
    }
}