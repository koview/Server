package com.koview.koview_server.product.service;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.global.common.purchaseLink.PurchaseLinkRepository;
import com.koview.koview_server.global.common.purchaseLink.PurchaseLinkResponseDTO;
import com.koview.koview_server.product.domain.Product;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductConverter;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.product.repository.ProductImageRepository;
import com.koview.koview_server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final PurchaseLinkRepository purchaseLinkRepository;

    @Override
    public ProductResponseDTO.ProductSlice getProductsByStatus(StatusType status, Pageable pageable) {
        Slice<Product> productSlice = productRepository.findAllByStatusOrderByIdDesc(status, pageable);

        return getProductSlice(productSlice);
    }

    @Override
    public ProductResponseDTO.ProductSlice getProducts(Pageable pageable) {
        Slice<Product> productSlice = productRepository.findAllBy(pageable);

        return getProductSlice(productSlice);
    }

    private ProductResponseDTO.ProductSlice getProductSlice(Slice<Product> productSlice) {
        List<ProductResponseDTO.Single> productList = productSlice.stream().map(product -> {
            List<ImageResponseDTO> imagePaths =
                    productImageRepository.findAllByProduct(product).stream().map(ProductConverter::toImageDTO).toList();

            List<PurchaseLinkResponseDTO> purchaseLinkList =
                    purchaseLinkRepository.findAllByProduct(product).stream().map(PurchaseLinkResponseDTO::new).toList();

            return ProductConverter.toSingleDTO(product, imagePaths, purchaseLinkList);
        }).toList();

        return ProductConverter.toSliceDTO(productSlice,productList);
    }


}
