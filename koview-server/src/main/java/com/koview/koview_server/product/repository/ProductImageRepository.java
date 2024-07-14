package com.koview.koview_server.product.repository;

import com.koview.koview_server.product.domain.Product;
import com.koview.koview_server.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findAllByProduct(Product product);
}
