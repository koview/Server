package com.koview.koview_server.product.service;

import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.CategoryType;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponseDTO.ProductSlice getProductsByStatus(Long categoryId, StatusType status, Pageable pageable);
    ProductResponseDTO.ProductSlice getProducts(Long categoryId, Pageable pageable);
    ProductResponseDTO.ProductSlice getProductsByStatusAndCategoryType(CategoryType category, StatusType status, Pageable pageable);
    ProductResponseDTO.ProductSlice getProductsByCategorytype(CategoryType category, Pageable pageable);

    List<Category> getCategories();
    ProductResponseDTO.Detail getProductDetail(Long productId, Pageable pageable);
    LimitedReviewResponseDTO.ReviewPaging getReviewsByProductId(Long productId, Pageable pageable);

}
