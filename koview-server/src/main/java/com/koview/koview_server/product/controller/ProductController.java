package com.koview.koview_server.product.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.product.service.ProductService;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @GetMapping("categories")
    @Operation(description = "카테고리 조회")
    public ApiResult<List<Category>> getCategories(){
        return ApiResult.onSuccess(productService.getCategories());
    }

    @GetMapping("products")
    @Operation(description = "전체/유해/인기 상품 조회")
    public ApiResult<ProductResponseDTO.ProductSlice> getProductsBy(
            @Parameter(description = "상품 상태 필터(null 입력시 적용 안됨)")
            @RequestParam(required = false) StatusType status,
            @Parameter(description = "카테고리 필터(null 입력시 적용 안됨)")
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지 크기(한 번에 보내는 리스트 양)")
            @RequestParam(defaultValue = "20") int size) {
        if (status == null) // 전체 상품 조회 로직
            return ApiResult.onSuccess(productService.getProducts(categoryId,PageRequest.of(page-1,size)));
        else  // 유해, 인기 상품 조회 로직
            return ApiResult.onSuccess(productService.getProductsByStatus(categoryId, status, PageRequest.of(page-1,size)));
    }

    @GetMapping("products/{productId}")
    @Operation(description = "상품 상세 조회(리뷰 무한스크롤은 products/{productId}/reviews 이용해주세요.)")
    public ApiResult<ProductResponseDTO.Detail> getProduct(
            @PathVariable Long productId,
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지 크기(한 번에 보내는 리스트 양)")
            @RequestParam(defaultValue = "20") int size
    ){
      return ApiResult.onSuccess(productService.getProductDetail(productId,PageRequest.of(page-1,size)));
    }

    @GetMapping("products/{productId}/reviews")
    @Operation(description = "상품 리뷰 조회")
    public ApiResult<LimitedReviewResponseDTO.ReviewPaging> getReviewsByProduct(
            @PathVariable Long productId,
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지 크기(한 번에 보내는 리스트 양)")
            @RequestParam(defaultValue = "20") int size
    ){
        return ApiResult.onSuccess(productService.getReviewsByProductId(productId,PageRequest.of(page-1,size)));
    }


}
