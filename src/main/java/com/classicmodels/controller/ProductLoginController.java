package com.classicmodels.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductLoginController {

    // URL: http://localhost:8080/product/login
    @GetMapping("/product/login")
    public String productLoginPage() {
        return "product/login";
    }
}