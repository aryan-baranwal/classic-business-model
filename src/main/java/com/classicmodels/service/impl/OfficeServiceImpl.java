package com.classicmodels.service.impl;
import com.classicmodels.entity.Employee;
import com.classicmodels.dto.OfficeResponseDto;
import com.classicmodels.entity.Office;
import com.classicmodels.exception.OfficeNotFoundException;
import com.classicmodels.repository.OfficeRepository;
import com.classicmodels.service.OfficeService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeServiceImpl(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public List<OfficeResponseDto> getAllOffices() {

        List<Office> offices = officeRepository.findAll();

        if (offices.isEmpty()) {
            throw new OfficeNotFoundException(
                    "No offices found"
            );
        }

        return offices.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OfficeResponseDto getOfficeByCode(String officeCode) {

        Office office = officeRepository
                .findById(officeCode)
                .orElseThrow(() ->
                        new OfficeNotFoundException(
                                "Office not found with code: "
                                        + officeCode
                        )
                );

        return mapToDto(office);
    }

    @Override
    public List<Employee> getEmployeesByOffice(String officeCode) {

        Office office = officeRepository.findById(officeCode)
                .orElseThrow(() ->
                        new OfficeNotFoundException(
                                "Office not found with code: " + officeCode
                        )
                );

        return office.getEmployees();
    }

    private OfficeResponseDto mapToDto(Office office) {

        return new OfficeResponseDto(
                office.getOfficeCode(),
                office.getCity(),
                office.getPhone(),
                office.getCountry(),
                office.getTerritory()
        );
    }
}