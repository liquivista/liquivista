package com.management.product.service;

import com.management.product.dto.ProductRequestDto;
import com.management.product.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequestDto productRequestDto);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(Long productId);
}
