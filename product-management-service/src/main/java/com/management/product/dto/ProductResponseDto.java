package com.management.product.dto;

import lombok.Builder;

@Builder
public record ProductResponseDto(Long productId, String productName, String productType, double productPrice,
                                 int productStockQuantity, String productSupplierId, String productCategory,
                                 String productImageDmsId, double productAverageRating, int productReviewCount) {
}
