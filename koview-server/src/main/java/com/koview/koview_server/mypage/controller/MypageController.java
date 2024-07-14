package com.koview.koview_server.mypage.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.global.security.util.SecurityUtil;
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
@RequestMapping("/mypage")
@Tag(name = "MyPage", description = "MyPage API")
public class MypageController {

    private final ReviewService reviewService;

    @GetMapping("/myreview")
    @Operation(description = "나의 리뷰 전체 조회")
    public ApiResult<?> findAllMyReview() {
        List<ReviewResponseDTO> responseDTOs = reviewService.findAllByMember();
        return ApiResult.onSuccess(responseDTOs);
    }

    @DeleteMapping("/myreview/{reviewId}/delete")
    @Operation(description = "나의 리뷰 삭제")
    public ApiResult<?> deleteMyReview(@PathVariable Long reviewId) {
        reviewService.deleteMyReview(reviewId);
        return ApiResult.onSuccess();
    }
}
