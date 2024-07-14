package com.koview.koview_server.product.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.product.domain.StatusType;
import com.koview.koview_server.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    @Operation(description = "전체/유해/인기 상품 조회")
    public ApiResult<?> getProductsBy(
            @Parameter(description = "상품 상태 필터(null 입력시 전체 상품 조회)")
            @RequestParam(required = false) StatusType status,
            @Parameter(description = "페이지 번호(1부터 시작), default: 1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지 크기(한 번에 보내는 리스트 양), default: 20")
            @RequestParam(defaultValue = "20") int size) {
        if (status == null) // 전체 상품 조회 로직
            return ApiResult.onSuccess(productService.getProducts(PageRequest.of(page-1,size)));
        else  // 유해, 인기 상품 조회 로직
            return ApiResult.onSuccess(productService.getProductsByStatus(status, PageRequest.of(page-1,size)));
    }
}
