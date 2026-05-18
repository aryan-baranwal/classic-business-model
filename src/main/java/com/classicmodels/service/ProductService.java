// ProductService.java
package com.classicmodels.service;

import com.classicmodels.dto.ProductDto;
import com.classicmodels.dto.ProductLineDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductByCode(String productCode);

    List<ProductLineDto> getAllProductLines();

    List<ProductDto> getProductsByProductLine(String productLine);
}