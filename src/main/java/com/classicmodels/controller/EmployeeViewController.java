package com.classicmodels.controller;

import com.classicmodels.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeViewController {

    private final EmployeeService employeeService;

    public EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // URL: /employees — list of all employees
    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee/list";
    }

    // URL: /employees/dashboard — in-page API tester
    @GetMapping("/dashboard")
    public String dashboard() {
        return "employee/dashboard";
    }
}
