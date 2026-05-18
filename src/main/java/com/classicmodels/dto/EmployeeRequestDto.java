package com.classicmodels.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class EmployeeRequestDto {

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

    @NotBlank(message = "Extension is required")
    @Size(
            max = 10,
            message = "Extension cannot exceed 10 characters"
    )
    private String extension;

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
    private String officeCode;

    private Integer managerId;

    // DEFAULT CONSTRUCTOR

    public EmployeeRequestDto() {
    }

    // PARAMETERIZED CONSTRUCTOR

    public EmployeeRequestDto(
            Integer employeeNumber,
            String firstName,
            String lastName,
            String extension,
            String email,
            String jobTitle,
            String officeCode,
            Integer managerId
    ) {

        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.extension = extension;
        this.email = email;
        this.jobTitle = jobTitle;
        this.officeCode = officeCode;
        this.managerId = managerId;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    // equals()

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof EmployeeRequestDto)) {
            return false;
        }

        EmployeeRequestDto that =
                (EmployeeRequestDto) o;

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

        return "EmployeeRequestDto{" +
                "employeeNumber=" + employeeNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", extension='" + extension + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", officeCode='" + officeCode + '\'' +
                ", managerId=" + managerId +
                '}';
    }
}