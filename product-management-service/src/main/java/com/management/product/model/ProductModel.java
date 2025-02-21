package com.management.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "product_service")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_stock_quantity")
    private int productStockQuantity;

    @Column(name = "product_supplier_id")
    private String productSupplierId;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_image_dms_id")
    private String productImageDmsId;

    @Column(name = "product_average_rating")
    private double productAverageRating;

    @Column(name = "product_review_count")
    private int productReviewCount;
}
