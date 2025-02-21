package com.management.product.dto;

public record ProductRequestDto(Long productId, String productName, String productType, double productPrice,
                                int productStockQuantity, String productSupplierId, String productCategory,
                                String productImageDmsId, double productAverageRating, int productReviewCount) {}
