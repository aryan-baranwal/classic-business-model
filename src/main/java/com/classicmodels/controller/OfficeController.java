package com.classicmodels.controller;

import com.classicmodels.service.OfficeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/offices")
@Validated
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public String getAllOffices(Model model) {

        model.addAttribute(
                "offices",
                officeService.getAllOffices()
        );

        return "office/list";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "office/dashboard";
    }

    @GetMapping("/{officeCode}")
    public String getOfficeByCode(
            @PathVariable
            @NotBlank(message = "Office code cannot be empty")
            @Size(max = 10, message = "Office code cannot exceed 10 characters")
            @Pattern(
                    regexp = "[1-9][0-9]*",
                    message = "Office code must be a positive whole number without spaces"
            )
            String officeCode,
            Model model) {

        model.addAttribute(
                "office",
                officeService.getOfficeByCode(officeCode)
        );

        return "office/details";
    }
}
