package com.classicmodels.controller;

import com.classicmodels.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    // URL: http://localhost:8080/view/products
    @GetMapping("/products")
    public String getAllProducts(Model model) {

        model.addAttribute(
                "products",
                productService.getAllProducts()
        );

        return "product/list";
    }

    // URL: http://localhost:8080/view/products/{productCode}
    @GetMapping("/products/{productCode}")
    public String getProductByCode(
            @PathVariable String productCode,
            Model model) {

        model.addAttribute(
                "product",
                productService.getProductByCode(productCode)
        );

        return "product/details";
    }

    // URL: http://localhost:8080/view/product-lines
    @GetMapping("/product-lines")
    public String getAllProductLines(Model model) {

        model.addAttribute(
                "productLines",
                productService.getAllProductLines()
        );

        return "productline/list";
    }

    // URL: http://localhost:8080/view/product-lines/{productLine}/products
    @GetMapping("/product-lines/{productLine}/products")
    public String getProductsByProductLine(
            @PathVariable String productLine,
            Model model) {

        model.addAttribute(
                "products",
                productService.getProductsByProductLine(productLine)
        );

        return "product/list";
    }
}