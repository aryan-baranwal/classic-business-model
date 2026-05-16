package com.classicmodels.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class EmployeeResponseDto {

    @NotNull(message = "Employee number is required")
    @Positive(message = "Employee number must be positive")
    private Integer employeeNumber;

    @NotBlank(message = "First name is required")
    @Size(
            min = 2,
            max = 50,
            message = "First name must be between 2 and 50 characters"
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(
            min = 2,
            max = 50,
            message = "Last name must be between 2 and 50 characters"
    )
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(
            max = 100,
            message = "Email cannot exceed 100 characters"
    )
    private String email;

    @NotBlank(message = "Job title is required")
    @Size(
            max = 50,
            message = "Job title cannot exceed 50 characters"
    )
    private String jobTitle;

    @NotBlank(message = "Office code is required")
    @Size(
            max = 10,
            message = "Office code cannot exceed 10 characters"
    )
    private String officeCode;

    // DEFAULT CONSTRUCTOR

    public EmployeeResponseDto() {
    }

    // PARAMETERIZED CONSTRUCTOR

    public EmployeeResponseDto(
            Integer employeeNumber,
            String firstName,
            String lastName,
            String email,
            String jobTitle,
            String officeCode
    ) {

        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.officeCode = officeCode;
    }

    // GETTERS AND SETTERS

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    // equals()

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof EmployeeResponseDto)) {
            return false;
        }

        EmployeeResponseDto that = (EmployeeResponseDto) o;

        return Objects.equals(
                employeeNumber,
                that.employeeNumber
        );
    }

    // hashCode()

    @Override
    public int hashCode() {

        return Objects.hash(employeeNumber);
    }

    // toString()

    @Override
    public String toString() {

        return "EmployeeResponseDto{" +
                "employeeNumber=" + employeeNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", officeCode='" + officeCode + '\'' +
                '}';
    }
}