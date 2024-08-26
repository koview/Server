package com.koview.koview_server.review.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review", description = "Review API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/create")
    @Operation(description = "리뷰 등록", security = @SecurityRequirement(name = "jwtAuth"))
    public ApiResult<ReviewResponseDTO.toReviewDTO> createReview(@RequestBody ReviewRequestDTO requestDTO) {
        ReviewResponseDTO.toReviewDTO responseDTO = reviewService.createReview(requestDTO);
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
    @Operation(description = "리뷰 전체 조회(코뷰 메인화면)", security = @SecurityRequirement(name = "jwtAuth"))
    public ApiResult<ReviewResponseDTO.ReviewSlice> getAllReviews(
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ApiResult.onSuccess(reviewService.findAll(PageRequest.of(page-1, size)));
    }

    @GetMapping("/reviews/detail")
    @Operation(description = "특정 유저의 리뷰 조회", security = @SecurityRequirement(name = "jwtAuth"))
    public ApiResult<ReviewResponseDTO.ReviewSlice> getReviewDetail(
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam Long clickedReviewId,
            @Parameter(description = "지금은 Null처리. 다음 버전은 memberId 필수로 받아야 합니다.")
            @RequestParam(required = false) Long memberId) {

        return ApiResult.onSuccess(reviewService.findAllByMember(PageRequest.of(page-1, size), clickedReviewId, memberId));
    }

    @GetMapping("/review/search")
    @Operation(description = "리뷰 검색")
    public ApiResult<ReviewResponseDTO.ReviewSlice> searchReviews(
            @RequestParam String keyword,
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResult.onSuccess(reviewService.searchReviews(keyword, PageRequest.of(page-1, size)));
    }
}
