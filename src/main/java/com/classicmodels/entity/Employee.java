package com.classicmodels.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    @Id
    @Column(name = "employeeNumber")
    @NotNull(message = "Employee number is required")
    private Integer employeeNumber;

    @Column(name = "lastName", nullable = false)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "firstName", nullable = false)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "extension", nullable = false)
    @NotBlank(message = "Extension is required")
    private String extension;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name = "jobTitle", nullable = false)
    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "officeCode")
    private Office office;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reportsTo")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Employee> subordinates;

    // Default Constructor
    public Employee() {
    }

    // Parameterized Constructor
    public Employee(Integer employeeNumber,
                    String lastName,
                    String firstName,
                    String extension,
                    String email,
                    String jobTitle,
                    Office office,
                    Employee manager,
                    List<Employee> subordinates) {

        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.extension = extension;
        this.email = email;
        this.jobTitle = jobTitle;
        this.office = office;
        this.manager = manager;
        this.subordinates = subordinates;
    }

    // Getters and Setters

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}