package com.koview.koview_server.review.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review", description = "Review API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/create")
    @Operation(description = "리뷰 등록")
    public ApiResult<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO requestDTO) {
        ReviewResponseDTO responseDTO = reviewService.createReview(requestDTO);
        return ApiResult.onSuccess(responseDTO);
    }

    @DeleteMapping("/reviews/{reviewId}/delete")
    @Operation(description = "리뷰 삭제")
    public ApiResult<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResult.onSuccess();
    }

    @DeleteMapping("/reviews/delete")
    @Operation(description = "리뷰 리스트 삭제")
    public ApiResult<?> deleteReview(@RequestBody ReviewRequestDTO.ReviewIdListDTO requestDTO) {
        reviewService.deleteReviewList(requestDTO);
        return ApiResult.onSuccess();
    }

    @GetMapping("/reviews")
    @Operation(description = "리뷰 전체 조회(이미지 2개 제한)")
    public ApiResult<List<LimitedReviewResponseDTO>> getAllReviews() {
        return ApiResult.onSuccess(reviewService.findAllWithLimitedImages());
    }

    @GetMapping("/reviews/detail")
    @Operation(description = "리뷰 상세 조회")
    public ApiResult<List<ReviewResponseDTO>> getReview() {
        return ApiResult.onSuccess(reviewService.findAll());
    }
}
