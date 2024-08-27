package com.koview.koview_server.api.product.service;

import com.koview.koview_server.api.product.domain.dto.ProductConverter;
import com.koview.koview_server.api.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.api.product.repository.CategoryRepository;
import com.koview.koview_server.api.image.repository.ProductImageRepository;
import com.koview.koview_server.api.product.repository.ProductRepository;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.GeneralException;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.user.relation.like.repository.LikeRepository;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.product.domain.CategoryType;
import com.koview.koview_server.api.common.purchaseLink.repository.PurchaseLinkRepository;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.api.product.domain.Category;
import com.koview.koview_server.api.product.domain.Product;
import com.koview.koview_server.api.product.domain.StatusType;
import com.koview.koview_server.api.user.review.domain.Review;
import com.koview.koview_server.api.user.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.api.user.review.domain.dto.ReviewConverter;
import com.koview.koview_server.api.user.review.repository.ReviewRepository;
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
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Override
    public ProductResponseDTO.ProductSlice getProductsByStatusTypeAndCategory(Long categoryId, StatusType status,
                                                                              String searchTerm, Pageable pageable) {
        Category category = getCategory(categoryId);
        Slice<Product> productSlice =
                productRepository.findAllByCategoryAndStatusTypeAndSearchTerm(category,status,searchTerm,pageable);
        return getProductSlice(productSlice);
    }

    @Override
    public ProductResponseDTO.ProductSlice getProductsByStatusTypeAndCategoryType(CategoryType category, StatusType status,
                                                                                  String searchTerm, Pageable pageable) {
        Slice<Product> productSlice =
                productRepository.findAllByCategoryTypeAndStatusTypeAndSearchTerm(category, status, searchTerm,pageable);
        return getProductSlice(productSlice);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public ProductResponseDTO.Detail getProductDetail(Long productId, Pageable pageable) {
        Member member = validateMember();
        Product product = getProduct(productId);
        List<ImageResponseDTO> imagePaths =
                productImageRepository.findAllByProduct(product).stream().map(ProductConverter::toImageDTO).toList();
        List<PurchaseLinkResponseDTO> purchaseLinkList =
                purchaseLinkRepository.findAllByProduct(product).stream().map(PurchaseLinkResponseDTO::new).toList();
        Page<Review> reviewPage = reviewRepository.findAllByProductPurchaseLink(productId,pageable);

        ProductResponseDTO.Single detail = ProductConverter.toSingleDTO(product, reviewPage.getTotalElements(), imagePaths, purchaseLinkList);
        List<LimitedReviewResponseDTO.Single> reviewList = reviewPage.stream().map(review -> {
            Boolean isLiked = likeRepository.existsByMemberAndReview(member, review);
            return ReviewConverter.toLimitedSingleDto(review, isLiked);
        }).toList();

        LimitedReviewResponseDTO.ReviewPaging reviewPaging = ReviewConverter.toLimitedPagingDTO(reviewPage,reviewList);

        return ProductConverter.toDetailDTO(detail,reviewPaging);
    }

    @Override
    public LimitedReviewResponseDTO.ReviewPaging getReviewsByProductId(Long productId, Pageable pageable) {
        Member member = validateMember();
        Page<Review> reviewPage = reviewRepository.findAllByProductPurchaseLink(productId,pageable);

        List<LimitedReviewResponseDTO.Single> reviewList = reviewPage.stream().map(review -> {
            Boolean isLiked = likeRepository.existsByMemberAndReview(member, review);
            return ReviewConverter.toLimitedSingleDto(review, isLiked);
        }).toList();

        return ReviewConverter.toLimitedPagingDTO(reviewPage,reviewList);
    }
    @Transactional
    protected ProductResponseDTO.ProductSlice getProductSlice(Slice<Product> productSlice) {

        List<ProductResponseDTO.Single> productList = productSlice.stream().map(product -> {
            List<ImageResponseDTO> imagePaths =
                    productImageRepository.findAllByProduct(product).stream().map(ProductConverter::toImageDTO).toList();

            List<PurchaseLinkResponseDTO> purchaseLinkList =
                    purchaseLinkRepository.findAllByProduct(product).stream().map(PurchaseLinkResponseDTO::new).toList();
            Long reviewCount = reviewRepository.countByProductPurchaseLink(product.getId());
            if(reviewCount>=10 && product.getStatusType().equals(StatusType.NORMAL)) {
                product.setStatusType(StatusType.FAMOUS);
                productRepository.save(product);
            }
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

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }


}
