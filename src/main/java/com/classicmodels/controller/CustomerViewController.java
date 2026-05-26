package com.classicmodels.controller;

import com.classicmodels.dto.CustomerRequestDTO;
import com.classicmodels.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.Default;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/customers")
@Validated
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Shows customer list page
    @GetMapping
    public String getAllCustomers(Model model) {
        model.addAttribute("customers",
                customerService.getAllCustomers());
        return "customer/list";
    }

    // Shows single customer details
    @GetMapping("/{customerNumber}")
    public String getCustomerById(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            Model model) {
        model.addAttribute("customer",
                customerService.getCustomerById(customerNumber));
        return "customer/details";
    }

    // Shows search results
    @GetMapping("/search")
    public String searchCustomers(
            @RequestParam
            @NotBlank(message = "Country must not be blank")
            String country,
            @RequestParam
            @NotBlank(message = "City must not be blank")
            String city,
            Model model) {
        model.addAttribute("customers",
                customerService.searchByCountryAndCity(country, city));
        return "customer/list";
    }

    // Shows create customer form
    @GetMapping("/create")
    public String showCreateForm() {
        return "customer/create";
    }

    // Handles create customer form submission
    @PostMapping("/create")
    public String createCustomer(
            @Validated({
                    Default.class,
                    CustomerRequestDTO.Create.class
            })
            @ModelAttribute CustomerRequestDTO request) {
        customerService.createCustomer(request);
        return "redirect:/customers";
    }

    // Shows edit customer form
    @GetMapping("/{customerNumber}/edit")
    public String showEditForm(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            Model model) {
        model.addAttribute("customer",
                customerService.getCustomerById(customerNumber));
        return "customer/edit";
    }

    // Handles edit customer form submission
    @PostMapping("/{customerNumber}/edit")
    public String updateCustomer(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            @Valid @ModelAttribute CustomerRequestDTO request) {
        customerService.updateCustomer(customerNumber, request);
        return "redirect:/customers";
    }

    // Handles delete
    @PostMapping("/{customerNumber}/delete")
    public String deleteCustomer(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber) {
        customerService.deleteCustomer(customerNumber);
        return "redirect:/customers";
    }

    // Shows the API dashboard for in-page testing
    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer/dashboard";
    }

    // Shows credit limit page
    @GetMapping("/{customerNumber}/credit-limit")
    public String showCreditLimit(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            Model model) {
        model.addAttribute("customer",
                customerService.getCustomerById(customerNumber));
        return "customer/credit-limit";
    }

    // Handles credit limit update
    @PostMapping("/{customerNumber}/credit-limit")
    public String updateCreditLimit(
            @PathVariable
            @Positive(message = "Customer number must be positive")
            Integer customerNumber,
            @RequestParam
            @PositiveOrZero(message = "Credit limit cannot be negative")
            BigDecimal creditLimit) {
        customerService.updateCreditLimit(customerNumber, creditLimit);
        return "redirect:/customers/" + customerNumber;
    }

}
