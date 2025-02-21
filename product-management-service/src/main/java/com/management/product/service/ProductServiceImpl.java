package com.management.product.service;

import com.management.product.dto.ProductRequestDto;
import com.management.product.dto.ProductResponseDto;
import com.management.product.model.ProductModel;
import com.management.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public String addProduct(ProductRequestDto productRequestDto) {
        try {
            ProductModel productModel = ProductModel.builder()
                    .productName(productRequestDto.productName())
                    .productType(productRequestDto.productType())
                    .productPrice(productRequestDto.productPrice())
                    .productStockQuantity(productRequestDto.productStockQuantity())
                    .productSupplierId(productRequestDto.productSupplierId())
                    .productCategory(productRequestDto.productCategory())
                    .productImageDmsId(productRequestDto.productImageDmsId())
                    .productAverageRating(productRequestDto.productAverageRating())
                    .productReviewCount(productRequestDto.productReviewCount())
                    .build();

            ProductModel response = productRepository.save(productModel);
            log.info("Product Saved Successfully With Product Id: {}", response.getProductId());
            return "Product Saved Successfully With Product Id: {}"+ response.getProductId();
        }catch(Exception e){
            log.error("Product Not Added Got Exception :"+e.getMessage());
            return null;
        }
    }


    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        productsList.forEach(product -> log.info("Product: {}", mapToProductResponse(product)));
        return productsList.stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        ProductModel productModel = productRepository.findByProductId(productId);
        if(productModel != null) {
            log.info("Product Response By Id With Product Id:{} and Product:{}", productId, mapToProductResponse(productModel));
            return mapToProductResponse(productModel);
        }
        return null;
    }

    private ProductResponseDto mapToProductResponse(ProductModel productsList) {
        return ProductResponseDto.builder()
                .productId(productsList.getProductId())
                .productName(productsList.getProductName())
                .productType(productsList.getProductType())
                .productPrice(productsList.getProductPrice())
                .productStockQuantity(productsList.getProductStockQuantity())
                .productSupplierId(productsList.getProductSupplierId())
                .productCategory(productsList.getProductCategory())
                .productImageDmsId(productsList.getProductImageDmsId())
                .productAverageRating(productsList.getProductAverageRating())
                .productReviewCount(productsList.getProductReviewCount())
                .build();
    }


}
