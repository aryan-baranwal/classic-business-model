package com.classicmodels.service;

import com.classicmodels.dto.OfficeResponseDto;
import com.classicmodels.entity.Office;
import com.classicmodels.exception.OfficeNotFoundException;
import com.classicmodels.repository.OfficeRepository;
import com.classicmodels.service.impl.OfficeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OfficeServiceImplTest {

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private OfficeServiceImpl officeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOfficesSuccess() {

        Office office = new Office();

        office.setOfficeCode("1");
        office.setCity("San Francisco");
        office.setPhone("123456789");
        office.setCountry("USA");
        office.setTerritory("NA");

        when(officeRepository.findAll())
                .thenReturn(Arrays.asList(office));

        List<OfficeResponseDto> offices =
                officeService.getAllOffices();

        assertEquals(1, offices.size());

        assertEquals(
                "San Francisco",
                offices.get(0).getCity()
        );
    }

    @Test
    void testGetAllOfficesEmptyList() {

        when(officeRepository.findAll())
                .thenReturn(Collections.emptyList());

        Exception exception = assertThrows(
                OfficeNotFoundException.class,
                () -> officeService.getAllOffices()
        );

        assertEquals(
                "No offices found",
                exception.getMessage()
        );
    }

    @Test
    void testGetOfficeByCodeSuccess() {

        Office office = new Office();

        office.setOfficeCode("1");
        office.setCity("Boston");
        office.setPhone("987654321");
        office.setCountry("USA");
        office.setTerritory("NA");

        when(officeRepository.findById("1"))
                .thenReturn(Optional.of(office));

        OfficeResponseDto dto =
                officeService.getOfficeByCode("1");

        assertEquals("Boston", dto.getCity());
    }

    @Test
    void testGetOfficeByCodeFailure() {

        when(officeRepository.findById("999"))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(
                OfficeNotFoundException.class,
                () -> officeService.getOfficeByCode("999")
        );

        assertEquals(
                "Office not found with code: 999",
                exception.getMessage()
        );
    }
}