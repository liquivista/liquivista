package com.management.product.service;

import com.management.product.dto.ProductRequestDto;
import com.management.product.dto.ProductResponseDto;
import com.management.product.model.DmsModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequestDto productRequestDto, DmsModel dmsModel);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(Long productId);

    ResponseEntity<?> downloadDocument(String dmsId);
}
