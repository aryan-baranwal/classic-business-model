package com.classicmodels.controller;

import com.classicmodels.service.OfficeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/offices")
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

    @GetMapping("/{officeCode}")
    public String getOfficeByCode(
            @PathVariable String officeCode,
            Model model) {

        model.addAttribute(
                "office",
                officeService.getOfficeByCode(officeCode)
        );

        return "office/details";
    }
}