package com.classicmodels.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfficeLoginController {

    @GetMapping("/office/login")
    public String officeLoginPage() {
        return "office/login";
    }
}