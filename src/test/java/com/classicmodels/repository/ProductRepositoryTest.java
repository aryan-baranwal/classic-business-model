// src/test/java/com/classicmodels/repository/ProductRepositoryTest.java
package com.classicmodels.repository;

import com.classicmodels.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // Tests endpoint: GET /api/products
    @Test
    public void testFindAllProducts() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
    }

    // Tests endpoint: GET /api/products/{productCode}
    @Test
    public void testFindProductById() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products);

        if (!products.isEmpty()) {
            String productCode = products.get(0).getProductCode();

            Optional<Product> product =
                    productRepository.findById(productCode);

            assertNotNull(product);
        }
    }

    // Tests endpoint: GET /api/product-lines/{productLine}/products
    @Test
    public void testFindProductsByProductLine() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products);

        if (!products.isEmpty()
                && products.get(0).getProductLine() != null) {

            String productLine =
                    products.get(0)
                            .getProductLine()
                            .getProductLine();

            List<Product> result =
                    productRepository.findByProductLine_ProductLine(
                            productLine);

            assertNotNull(result);
        }
    }
}