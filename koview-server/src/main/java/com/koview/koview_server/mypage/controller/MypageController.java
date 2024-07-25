package com.koview.koview_server.mypage.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.mypage.service.MypageService;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "MyPage", description = "MyPage API")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/myreviews")
    @Operation(description = "나의 리뷰 전체 조회")
    public ApiResult<Slice<LimitedReviewResponseDTO>> findAllMyReview(@RequestParam(defaultValue = "0") int page) {
        return ApiResult.onSuccess(mypageService.findAllByMemberWithLimitedImages(PageRequest.of(page, 20)));
    }

    @GetMapping("/myreviews/detail")
    @Operation(description = "나의 리뷰 상세 조회")
    public ApiResult<Slice<ReviewResponseDTO>> findAllMyReviewDetail(@RequestParam(defaultValue = "0") int page) {
        return ApiResult.onSuccess(mypageService.findAllByMember(PageRequest.of(page, 20)));
    }

    @DeleteMapping("/myreviews/{reviewId}/delete")
    @Operation(description = "나의 리뷰 삭제")
    public ApiResult<?> deleteMyReview(@PathVariable Long reviewId) {
        mypageService.deleteMyReview(reviewId);
        return ApiResult.onSuccess();
    }

    @DeleteMapping("/myreviews/delete")
    @Operation(description = "나의 리뷰 리스트 삭제")
    public ApiResult<?> deleteReview(@RequestBody ReviewRequestDTO.ReviewIdListDTO requestDTO) {
        mypageService.deleteMyReviewList(requestDTO);
        return ApiResult.onSuccess();
    }
}
