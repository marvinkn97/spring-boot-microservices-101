package dev.marvin.service;

import dev.marvin.dto.ProductRequest;
import dev.marvin.model.Product;
import dev.marvin.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProduct(ProductRequest productRequest) {
        log.info("Adding product...");

        var product = Product.builder()
                .productName(productRequest.name())
                .productPrice(productRequest.price())
                .productQty(productRequest.quantity())
                .build();

        try {
            var savedProduct = productRepository.save(product);
            log.info("Product added {}", savedProduct);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to add product");
        }

    }
}
