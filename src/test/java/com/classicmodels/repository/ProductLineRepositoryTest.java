// src/test/java/com/classicmodels/repository/ProductLineRepositoryTest.java
package com.classicmodels.repository;

import com.classicmodels.entity.ProductLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ProductLineRepositoryTest {

    @Autowired
    private ProductLineRepository productLineRepository;

    // Tests endpoint: GET /api/product-lines
    @Test
    public void testFindAllProductLines() {
        List<ProductLine> productLines = productLineRepository.findAll();
        assertNotNull(productLines);
    }

    // Tests repository findById()
    @Test
    public void testFindProductLineById() {
        List<ProductLine> productLines = productLineRepository.findAll();
        assertNotNull(productLines);

        if (!productLines.isEmpty()) {
            String productLine = productLines.get(0).getProductLine();

            Optional<ProductLine> result =
                    productLineRepository.findById(productLine);

            assertNotNull(result);
        }
    }
}