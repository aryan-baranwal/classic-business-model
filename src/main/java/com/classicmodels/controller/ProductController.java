package com.classicmodels.controller;

import com.classicmodels.dto.ProductDto;
import com.classicmodels.dto.ProductLineDto;
import com.classicmodels.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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
            @PathVariable String productCode) {
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
            @PathVariable String productLine) {
        return productService.getProductsByProductLine(productLine);
    }
}