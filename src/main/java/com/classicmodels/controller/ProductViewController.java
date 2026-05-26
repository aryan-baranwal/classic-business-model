package com.classicmodels.controller;

import com.classicmodels.service.ProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    // URL: http://localhost:8080/products
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute(
                "products",
                productService.getAllProducts()
        );
        return "product/list";
    }

    // URL: http://localhost:8080/products/{productCode}
    @GetMapping("/products/{productCode}")
    public String getProductByCode(
            @PathVariable
            @NotBlank(message = "Product code is required")
            @Size(max = 15, message = "Product code must not exceed 15 characters")
            @Pattern(
                    regexp = "^(?!0$)(?!-)[^\\s]+$",
                    message = "Product code must not be 0, negative, or contain spaces"
            )
            String productCode,
            Model model) {

        model.addAttribute(
                "product",
                productService.getProductByCode(productCode)
        );

        return "product/details";
    }

    // URL: http://localhost:8080/products/dashboard
    @GetMapping("/products/dashboard")
    public String productDashboard() {
        return "product/dashboard";
    }

    // URL: http://localhost:8080/product-lines
    @GetMapping("/product-lines")
    public String getAllProductLines(Model model) {
        model.addAttribute(
                "productLines",
                productService.getAllProductLines()
        );
        return "productline/list";
    }

    // URL: http://localhost:8080/product-lines/{productLine}/products
    @GetMapping("/product-lines/{productLine}/products")
    public String getProductsByProductLine(
            @PathVariable
            @NotBlank(message = "Product line is required")
            @Size(max = 50, message = "Product line must not exceed 50 characters")
            @Pattern(
                    regexp = "^(?!0$)(?!-)(?!\\s)(?=.*[A-Za-z]).*\\S$",
                    message = "Product line must contain letters and must not be 0, negative, or start/end with spaces"
            )
            String productLine,
            Model model) {

        model.addAttribute(
                "products",
                productService.getProductsByProductLine(productLine)
        );

        return "product/list";
    }
}
