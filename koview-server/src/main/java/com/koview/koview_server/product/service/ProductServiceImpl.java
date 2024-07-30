package com.koview.koview_server.product.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.GeneralException;
import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.purchaseLink.repository.PurchaseLinkRepository;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.Product;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductConverter;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.product.repository.CategoryRepository;
import com.koview.koview_server.product.repository.ProductImageRepository;
import com.koview.koview_server.product.repository.ProductRepository;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewConverter;
import com.koview.koview_server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ProductResponseDTO.ProductSlice getProductsByStatus(Long categoryId, StatusType status, Pageable pageable) {
        Slice<Product> productSlice;
        if(categoryId==null) productSlice = productRepository.findAllByStatusOrderByIdDesc(status, pageable);
        else {
            Category category = getCategory(categoryId);
            productSlice = productRepository.findAllByCategoryAndStatusOrderByIdDesc(category,status, pageable);
        }
        return getProductSlice(productSlice);
    }

    @Override
    public ProductResponseDTO.ProductSlice getProducts(Long categoryId, Pageable pageable) {
        Slice<Product> productSlice;
        if(categoryId==null) productSlice = productRepository.findAllBy(pageable);
        else {
            Category category = getCategory(categoryId);
            productSlice = productRepository.findAllByCategory(category,pageable);
        }
        return getProductSlice(productSlice);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public ProductResponseDTO.Detail getProductDetail(Long productId, Pageable pageable) {
        Product product = getProduct(productId);
        List<ImageResponseDTO> imagePaths =
                productImageRepository.findAllByProduct(product).stream().map(ProductConverter::toImageDTO).toList();
        List<PurchaseLinkResponseDTO> purchaseLinkList =
                purchaseLinkRepository.findAllByProduct(product).stream().map(PurchaseLinkResponseDTO::new).toList();
        Page<Review> reviewPage = reviewRepository.findAllByProductPurchaseLink(productId,pageable);

        ProductResponseDTO.Single detail = ProductConverter.toSingleDTO(product, reviewPage.getTotalElements(), imagePaths, purchaseLinkList);
        List<LimitedReviewResponseDTO.Single> reviewList = reviewPage.stream().map(ReviewConverter::toLimitedSingleDto).toList();
        LimitedReviewResponseDTO.ReviewPaging reviewPaging = ReviewConverter.toLimitedPagingDTO(reviewPage,reviewList);

        return ProductConverter.toDetailDTO(detail,reviewPaging);
    }

    private ProductResponseDTO.ProductSlice getProductSlice(Slice<Product> productSlice) {

        List<ProductResponseDTO.Single> productList = productSlice.stream().map(product -> {
            List<ImageResponseDTO> imagePaths =
                    productImageRepository.findAllByProduct(product).stream().map(ProductConverter::toImageDTO).toList();

            List<PurchaseLinkResponseDTO> purchaseLinkList =
                    purchaseLinkRepository.findAllByProduct(product).stream().map(PurchaseLinkResponseDTO::new).toList();
            Long reviewCount = reviewRepository.countByProductPurchaseLink(product.getId());
            return ProductConverter.toSingleDTO(product, reviewCount, imagePaths, purchaseLinkList);
        }).toList();

        return ProductConverter.toSliceDTO(productSlice,productList);
    }

    private Category getCategory(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(
                ()->new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND));
    }

    private Product getProduct(Long productId){
        return productRepository.findById(productId).orElseThrow(()->new GeneralException(ErrorStatus.PRODUCT_NOT_FOUND));
    }


}
