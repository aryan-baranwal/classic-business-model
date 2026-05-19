package com.classicmodels.service;
import com.classicmodels.service.impl.ProductServiceImpl;

import com.classicmodels.dto.ProductDto;
import com.classicmodels.dto.ProductLineDto;
import com.classicmodels.entity.Product;
import com.classicmodels.entity.ProductLine;
import com.classicmodels.exception.ProductNotFoundException;
import com.classicmodels.repository.ProductLineRepository;
import com.classicmodels.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductLineRepository productLineRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testGetAllProducts() {
        ProductLine productLine = new ProductLine();
        productLine.setProductLine("Motorcycles");
        productLine.setTextDescription("Motorcycle products");

        Product product = new Product();
        product.setProductCode("S10_1678");
        product.setProductName("1969 Harley Davidson Ultimate Chopper");
        product.setQuantityInStock((short) 7933);
        product.setBuyPrice(new BigDecimal("48.81"));
        product.setProductLine(productLine);

        when(productRepository.findAll())
                .thenReturn(Collections.singletonList(product));

        List<ProductDto> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("S10_1678", products.get(0).getProductCode());
        assertEquals("Motorcycles", products.get(0).getProductLine());
    }

    @Test
    public void testGetProductByCode() {
        ProductLine productLine = new ProductLine();
        productLine.setProductLine("Motorcycles");

        Product product = new Product();
        product.setProductCode("S10_1678");
        product.setProductName("1969 Harley Davidson Ultimate Chopper");
        product.setQuantityInStock((short)7933);
        product.setBuyPrice(new BigDecimal("48.81"));
        product.setProductLine(productLine);

        when(productRepository.findById("S10_1678"))
                .thenReturn(Optional.of(product));

        ProductDto productDto =
                productService.getProductByCode("S10_1678");

        assertNotNull(productDto);
        assertEquals("S10_1678", productDto.getProductCode());
        assertEquals("Motorcycles", productDto.getProductLine());
    }

    @Test
    public void testGetProductByCodeThrowsException() {
        when(productRepository.findById("INVALID"))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductByCode("INVALID")
        );
    }

    @Test
    public void testGetAllProductLines() {
        ProductLine productLine = new ProductLine();
        productLine.setProductLine("Motorcycles");
        productLine.setTextDescription("Motorcycle products");

        when(productLineRepository.findAll())
                .thenReturn(Collections.singletonList(productLine));

        List<ProductLineDto> productLines =
                productService.getAllProductLines();

        assertNotNull(productLines);
        assertEquals(1, productLines.size());
        assertEquals(
                "Motorcycles",
                productLines.get(0).getProductLine()
        );
    }

    @Test
    public void testGetProductsByProductLine() {
        ProductLine productLine = new ProductLine();
        productLine.setProductLine("Motorcycles");

        Product product = new Product();
        product.setProductCode("S10_1678");
        product.setProductName("1969 Harley Davidson Ultimate Chopper");
        product.setQuantityInStock((short) 7933);
        product.setBuyPrice(new BigDecimal("48.81"));
        product.setProductLine(productLine);

        when(productRepository
                .findByProductLine_ProductLine("Motorcycles"))
                .thenReturn(Collections.singletonList(product));

        List<ProductDto> products =
                productService.getProductsByProductLine("Motorcycles");

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(
                "Motorcycles",
                products.get(0).getProductLine()
        );
    }
}