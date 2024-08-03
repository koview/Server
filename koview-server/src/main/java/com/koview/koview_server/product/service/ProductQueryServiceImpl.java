package com.koview.koview_server.product.service;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.product.domain.ProductImage;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.product.repository.ProductImageRepository;
import com.koview.koview_server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public List<ProductResponseDTO.NameWithImage> getFourRestrictedProducts() {
        return productRepository.findTop4ByStatusTypeOrderByRestrictedDateDesc(
                StatusType.RESTRICTED,PageRequest.of(0,4)).stream().map(product -> {
                    ProductImage first = productImageRepository.findAllByProduct(product).getFirst();
                    return new ProductResponseDTO.NameWithImage(product.getProductName(), new ImageResponseDTO((first)));
                }).toList();
    }

    @Override
    public List<ProductResponseDTO.NameWithImage> getFourBestFamousProducts() {
        return productRepository.findTop4ProductsByStatusAndReviewCount(StatusType.FAMOUS, PageRequest.of(0, 4))
                .stream().map(product -> {
                    ProductImage productImage = productImageRepository.findAllByProduct(product).getFirst();
                    return new ProductResponseDTO.NameWithImage(product.getProductName(), new ImageResponseDTO(productImage));
                }).toList();
    }
}
