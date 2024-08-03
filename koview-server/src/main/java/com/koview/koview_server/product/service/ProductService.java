package com.koview.koview_server.product.service;

import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.CategoryType;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponseDTO.ProductSlice getProductsByStatusTypeAndCategory(Long categoryId, StatusType status, String searchTerm, Pageable pageable);
    ProductResponseDTO.ProductSlice getProductsByStatusTypeAndCategoryType(CategoryType category, StatusType status, String searchTerm, Pageable pageable);
    List<Category> getCategories();
    ProductResponseDTO.Detail getProductDetail(Long productId, Pageable pageable);
    LimitedReviewResponseDTO.ReviewPaging getReviewsByProductId(Long productId, Pageable pageable);

}
