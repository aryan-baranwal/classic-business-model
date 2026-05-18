package com.classicmodels.service.Impl;

import com.classicmodels.dto.ProductDto;
import com.classicmodels.dto.ProductLineDto;
import com.classicmodels.entity.Product;
import com.classicmodels.entity.ProductLine;
import com.classicmodels.exception.ProductNotFoundException;
import com.classicmodels.repository.ProductLineRepository;
import com.classicmodels.repository.ProductRepository;
import com.classicmodels.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductLineRepository productLineRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductLineRepository productLineRepository) {
        this.productRepository = productRepository;
        this.productLineRepository = productLineRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductByCode(String productCode) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        return convertToProductDto(product);
    }

    @Override
    public List<ProductLineDto> getAllProductLines() {
        return productLineRepository.findAll()
                .stream()
                .map(this::convertToProductLineDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByProductLine(String productLine) {
        return productRepository.findByProductLine_ProductLine(productLine)
                .stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToProductDto(Product product) {
        return new ProductDto(
                product.getProductCode(),
                product.getProductName(),
                product.getQuantityInStock(),
                product.getBuyPrice(),
                product.getProductLine().getProductLine()
        );
    }

    private ProductLineDto convertToProductLineDto(ProductLine productLine) {
        return new ProductLineDto(
                productLine.getProductLine(),
                productLine.getTextDescription()
        );
    }
}