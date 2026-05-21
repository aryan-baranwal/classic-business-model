package com.classicmodels.controller;

import com.classicmodels.dto.CustomerRequestDTO;
import com.classicmodels.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/customers")
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
            @PathVariable Integer customerNumber,
            Model model) {
        model.addAttribute("customer",
                customerService.getCustomerById(customerNumber));
        return "customer/details";
    }

    // Shows search results
    @GetMapping("/search")
    public String searchCustomers(
            @RequestParam String country,
            @RequestParam String city,
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
    public String createCustomer(@ModelAttribute CustomerRequestDTO request) {
        customerService.createCustomer(request);
        return "redirect:/customers";
    }

    // Shows edit customer form
    @GetMapping("/{customerNumber}/edit")
    public String showEditForm(
            @PathVariable Integer customerNumber,
            Model model) {
        model.addAttribute("customer",
                customerService.getCustomerById(customerNumber));
        return "customer/edit";
    }

    // Handles edit customer form submission
    @PostMapping("/{customerNumber}/edit")
    public String updateCustomer(
            @PathVariable Integer customerNumber,
            @ModelAttribute CustomerRequestDTO request) {
        customerService.updateCustomer(customerNumber, request);
        return "redirect:/customers";
    }

    // Handles delete
    @PostMapping("/{customerNumber}/delete")
    public String deleteCustomer(@PathVariable Integer customerNumber) {
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
            @PathVariable Integer customerNumber,
            Model model) {
        model.addAttribute("customer",
                customerService.getCustomerById(customerNumber));
        return "customer/credit-limit";
    }

    // Handles credit limit update
    @PostMapping("/{customerNumber}/credit-limit")
    public String updateCreditLimit(
            @PathVariable Integer customerNumber,
            @RequestParam BigDecimal creditLimit) {
        customerService.updateCreditLimit(customerNumber, creditLimit);
        return "redirect:/customers/" + customerNumber;
    }

}