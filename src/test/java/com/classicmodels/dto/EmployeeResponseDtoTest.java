package com.classicmodels.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeResponseDtoTest {

    @Test
    void testEmployeeResponseDtoCreation() {

        EmployeeResponseDto dto =
                new EmployeeResponseDto();

        dto.setEmployeeNumber(1002);
        dto.setFirstName("Diane");
        dto.setLastName("Murphy");
        dto.setEmail("dmurphy@classicmodelcars.com");
        dto.setJobTitle("President");
        dto.setOfficeCode("1");

        assertEquals(
                1002,
                dto.getEmployeeNumber()
        );

        assertEquals(
                "Diane",
                dto.getFirstName()
        );
    }
}