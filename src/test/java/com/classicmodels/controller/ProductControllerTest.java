package com.classicmodels.controller;

import com.classicmodels.dto.ProductDto;
import com.classicmodels.dto.ProductLineDto;
import com.classicmodels.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * JUnit Test for ProductController (REST Controller)
 */
@WebMvcTest(ProductController.class)
@WithMockUser
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    /**
     * Test GET /api/products
     */
    @Test
    @DisplayName("Should return all products")
    void shouldReturnAllProducts() throws Exception {

        ProductDto product = new ProductDto();
        product.setProductCode("S10_1678");
        product.setProductName("1969 Harley Davidson Ultimate Chopper");
        product.setQuantityInStock((short) 7933);
        product.setBuyPrice(new BigDecimal("48.81"));
        product.setProductLine("Motorcycles");

        when(productService.getAllProducts())
                .thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productCode")
                        .value("S10_1678"))
                .andExpect(jsonPath("$[0].productName")
                        .value("1969 Harley Davidson Ultimate Chopper"))
                .andExpect(jsonPath("$[0].productLine")
                        .value("Motorcycles"));
    }

    /**
     * Test GET /api/products/{productCode}
     */
    @Test
    @DisplayName("Should return product by code")
    void shouldReturnProductByCode() throws Exception {

        ProductDto product = new ProductDto();
        product.setProductCode("S10_1678");
        product.setProductName("1969 Harley Davidson Ultimate Chopper");
        product.setQuantityInStock((short) 7933);
        product.setBuyPrice(new BigDecimal("48.81"));
        product.setProductLine("Motorcycles");

        when(productService.getProductByCode("S10_1678"))
                .thenReturn(product);

        mockMvc.perform(get("/api/products/S10_1678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode")
                        .value("S10_1678"))
                .andExpect(jsonPath("$.productLine")
                        .value("Motorcycles"));
    }

    /**
     * Test GET /api/product-lines
     */
    @Test
    @DisplayName("Should return all product lines")
    void shouldReturnAllProductLines() throws Exception {

        ProductLineDto productLine = new ProductLineDto();
        productLine.setProductLine("Motorcycles");
        productLine.setTextDescription("Motorcycle models");

        when(productService.getAllProductLines())
                .thenReturn(Collections.singletonList(productLine));

        mockMvc.perform(get("/api/product-lines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productLine")
                        .value("Motorcycles"))
                .andExpect(jsonPath("$[0].textDescription")
                        .value("Motorcycle models"));
    }

    /**
     * Test GET /api/product-lines/{productLine}/products
     */
    @Test
    @DisplayName("Should return products by product line")
    void shouldReturnProductsByProductLine() throws Exception {

        ProductDto product = new ProductDto();
        product.setProductCode("S10_1678");
        product.setProductName("1969 Harley Davidson Ultimate Chopper");
        product.setQuantityInStock((short) 7933);
        product.setBuyPrice(new BigDecimal("48.81"));
        product.setProductLine("Motorcycles");

        when(productService.getProductsByProductLine("Motorcycles"))
                .thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/api/product-lines/Motorcycles/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productCode")
                        .value("S10_1678"))
                .andExpect(jsonPath("$[0].productLine")
                        .value("Motorcycles"));
    }
}