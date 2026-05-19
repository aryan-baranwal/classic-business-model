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
import static org.mockito.Mockito.*;

class OfficeServiceImplTest {

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private OfficeServiceImpl officeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Office createOffice() {

        Office office = new Office();

        office.setOfficeCode("1");
        office.setCity("San Francisco");
        office.setPhone("123456789");
        office.setCountry("USA");
        office.setTerritory("NA");

        return office;
    }

    @Test
    void testGetAllOfficesSuccess() {

        Office office = createOffice();

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

        Office office = createOffice();

        when(officeRepository.findById("1"))
                .thenReturn(Optional.of(office));

        OfficeResponseDto dto =
                officeService.getOfficeByCode("1");

        assertEquals("San Francisco", dto.getCity());
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

    @Test
    void testGetAllOfficesReturnsCorrectOfficeCode() {

        Office office = createOffice();

        when(officeRepository.findAll())
                .thenReturn(Arrays.asList(office));

        List<OfficeResponseDto> offices =
                officeService.getAllOffices();

        assertEquals(
                "1",
                offices.get(0).getOfficeCode()
        );
    }

    @Test
    void testGetAllOfficesReturnsCorrectPhone() {

        Office office = createOffice();

        when(officeRepository.findAll())
                .thenReturn(Arrays.asList(office));

        List<OfficeResponseDto> offices =
                officeService.getAllOffices();

        assertEquals(
                "123456789",
                offices.get(0).getPhone()
        );
    }

    @Test
    void testGetAllOfficesReturnsCorrectCountry() {

        Office office = createOffice();

        when(officeRepository.findAll())
                .thenReturn(Arrays.asList(office));

        List<OfficeResponseDto> offices =
                officeService.getAllOffices();

        assertEquals(
                "USA",
                offices.get(0).getCountry()
        );
    }

    @Test
    void testGetAllOfficesReturnsCorrectTerritory() {

        Office office = createOffice();

        when(officeRepository.findAll())
                .thenReturn(Arrays.asList(office));

        List<OfficeResponseDto> offices =
                officeService.getAllOffices();

        assertEquals(
                "NA",
                offices.get(0).getTerritory()
        );
    }

    @Test
    void testRepositoryFindAllCalledOnce() {

        Office office = createOffice();

        when(officeRepository.findAll())
                .thenReturn(Arrays.asList(office));

        officeService.getAllOffices();

        verify(officeRepository, times(1))
                .findAll();
    }

    @Test
    void testRepositoryFindByIdCalledOnce() {

        Office office = createOffice();

        when(officeRepository.findById("1"))
                .thenReturn(Optional.of(office));

        officeService.getOfficeByCode("1");

        verify(officeRepository, times(1))
                .findById("1");
    }
}