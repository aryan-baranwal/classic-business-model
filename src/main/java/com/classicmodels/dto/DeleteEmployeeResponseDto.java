package com.classicmodels.dto;

public class DeleteEmployeeResponseDto {

    private String message;

    private EmployeeResponseDto employee;

    public DeleteEmployeeResponseDto() {
    }

    public DeleteEmployeeResponseDto(
            String message,
            EmployeeResponseDto employee
    ) {

        this.message = message;
        this.employee = employee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message
    ) {
        this.message = message;
    }

    public EmployeeResponseDto getEmployee() {
        return employee;
    }

    public void setEmployee(
            EmployeeResponseDto employee
    ) {
        this.employee = employee;
    }
}