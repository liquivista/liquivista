package com.management.product.controller;


import com.management.product.dto.ProductRequestDto;
import com.management.product.dto.ProductResponseDto;
import com.management.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-management")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        String response = productService.addProduct(productRequestDto);

        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Product creation failed. Please try again.", HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();

        if (products != null && !products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get-product-by-id")
    public ResponseEntity<ProductResponseDto> getProductById(@RequestParam Long productId) {
        ProductResponseDto product = productService.getProductById(productId);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
