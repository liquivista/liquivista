package com.management.product.repository;

import com.management.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel , Long> {
    ProductModel findByProductId(Long productId);
}
