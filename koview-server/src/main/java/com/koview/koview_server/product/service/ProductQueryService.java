package com.koview.koview_server.product.service;

import com.koview.koview_server.product.domain.dto.ProductResponseDTO;

import java.util.List;
/**
 *  다른 도메인에서 사용가능한 비즈니스 로직
 */
public interface ProductQueryService {
    /**
    * home api:
    * 유해상품 4건 리턴
    * 인기상품 4건 리턴
    */
    List<ProductResponseDTO.NameWithImage> getFourRestrictedProducts();
    List<ProductResponseDTO.NameWithImage> getFourBestFamousProducts();

}
