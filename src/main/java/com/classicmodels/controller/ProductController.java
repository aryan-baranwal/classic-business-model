package com.classicmodels.controller;

import com.classicmodels.dto.ProductDto;
import com.classicmodels.dto.ProductLineDto;
import com.classicmodels.service.ProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products
    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET /api/products/{productCode}
    @GetMapping("/products/{productCode}")
    public ProductDto getProductByCode(
            @PathVariable
            @NotBlank(message = "Product code is required")
            @Size(max = 15, message = "Product code must not exceed 15 characters")
            @Pattern(
                    regexp = "^(?!0$)(?!-)[^\\s]+$",
                    message = "Product code must not be 0, negative, or contain spaces"
            )
            String productCode) {
        return productService.getProductByCode(productCode);
    }

    // GET /api/product-lines
    @GetMapping("/product-lines")
    public List<ProductLineDto> getAllProductLines() {
        return productService.getAllProductLines();
    }

    // GET /api/product-lines/{productLine}/products
    @GetMapping("/product-lines/{productLine}/products")
    public List<ProductDto> getProductsByProductLine(
            @PathVariable
            @NotBlank(message = "Product line is required")
            @Size(max = 50, message = "Product line must not exceed 50 characters")
            @Pattern(
                    regexp = "^(?!0$)(?!-)(?!\\s)(?=.*[A-Za-z]).*\\S$",
                    message = "Product line must contain letters and must not be 0, negative, or start/end with spaces"
            )
            String productLine) {
        return productService.getProductsByProductLine(productLine);
    }
}
