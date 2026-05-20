package com.classicmodels.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class Employee {

    @Id
    @Column(name = "employeeNumber")
    @NotNull(message = "Employee number is required")
    @Positive(message = "Employee number must be positive")
    private Integer employeeNumber;

    @Column(
            name = "lastName",
            nullable = false,
            length = 50
    )
    @NotBlank(message = "Last name is required")
    @Size(
            min = 2,
            max = 50,
            message = "Last name must be between 2 and 50 characters"
    )
    private String lastName;

    @Column(
            name = "firstName",
            nullable = false,
            length = 50
    )
    @NotBlank(message = "First name is required")
    @Size(
            min = 2,
            max = 50,
            message = "First name must be between 2 and 50 characters"
    )
    private String firstName;

    @Column(
            name = "extension",
            nullable = false,
            length = 10
    )
    @NotBlank(message = "Extension is required")
    @Size(
            max = 10,
            message = "Extension cannot exceed 10 characters"
    )
    private String extension;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            length = 100
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(
            max = 100,
            message = "Email cannot exceed 100 characters"
    )
    private String email;

    @Column(
            name = "jobTitle",
            nullable = false,
            length = 50
    )
    @NotBlank(message = "Job title is required")
    @Size(
            max = 50,
            message = "Job title cannot exceed 50 characters"
    )
    private String jobTitle;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "officeCode",
            nullable = false
    )
    @NotNull(message = "Office is required")
    private Office office;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reportsTo")
    @JsonIgnoreProperties({
            "manager",
            "subordinates"
    })
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Employee> subordinates;

    // REQUIRED FOR REPORT ENDPOINTS

    @OneToMany(mappedBy = "salesRepEmployee")
    @JsonIgnore
    private List<Customer> customers;

    // DEFAULT CONSTRUCTOR

    public Employee() {
    }

    // PARAMETERIZED CONSTRUCTOR

    public Employee(
            Integer employeeNumber,
            String lastName,
            String firstName,
            String extension,
            String email,
            String jobTitle,
            Office office,
            Employee manager,
            List<Employee> subordinates,
            List<Customer> customers
    ) {

        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.extension = extension;
        this.email = email;
        this.jobTitle = jobTitle;
        this.office = office;
        this.manager = manager;
        this.subordinates = subordinates;
        this.customers = customers;
    }

    // GETTERS AND SETTERS

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(
            Integer employeeNumber
    ) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(
            String lastName
    ) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(
            String firstName
    ) {
        this.firstName = firstName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(
            String extension
    ) {
        this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email
    ) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(
            String jobTitle
    ) {
        this.jobTitle = jobTitle;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(
            Office office
    ) {
        this.office = office;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(
            Employee manager
    ) {
        this.manager = manager;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(
            List<Employee> subordinates
    ) {
        this.subordinates = subordinates;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(
            List<Customer> customers
    ) {
        this.customers = customers;
    }

    // equals()

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Employee)) {
            return false;
        }

        Employee employee = (Employee) o;

        return Objects.equals(
                employeeNumber,
                employee.employeeNumber
        );
    }

    // hashCode()

    @Override
    public int hashCode() {

        return Objects.hash(
                employeeNumber
        );
    }

    // toString()

    @Override
    public String toString() {

        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", extension='" + extension + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}