package com.koview.koview_server.product.service;

import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponseDTO.ProductSlice getProductsByStatus(Long categoryId, StatusType status, Pageable pageable);
    ProductResponseDTO.ProductSlice getProducts(Long categoryId, Pageable pageable);
    List<Category> getCategories();
    ProductResponseDTO.Detail getProductDetail(Long productId, Pageable pageable);

}
