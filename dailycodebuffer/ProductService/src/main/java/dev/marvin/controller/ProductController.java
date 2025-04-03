package dev.marvin.controller;

import dev.marvin.dto.ProductRequest;
import dev.marvin.model.Response;
import dev.marvin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Response> addProduct(@RequestBody ProductRequest productRequest) {
        productService.addProduct(productRequest);
        var response = new Response(LocalDateTime.now(), HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), "Product added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
