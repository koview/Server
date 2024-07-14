package com.koview.koview_server.product.service;

import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDTO.ProductSlice getProductsByStatus(StatusType status, Pageable pageable);
    ProductResponseDTO.ProductSlice getProducts(Pageable pageable);

}
