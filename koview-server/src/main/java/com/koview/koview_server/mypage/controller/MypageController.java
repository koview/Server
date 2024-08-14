package com.koview.koview_server.mypage.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.mypage.domain.dto.MyProfileResponseDTO;
import com.koview.koview_server.mypage.service.MypageService;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "MyPage", description = "MyPage API")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/myreviews")
    @Operation(description = "나의 리뷰 전체 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "나의 리뷰 전체 조회 성공",
            content = @Content(schema = @Schema(implementation = LimitedReviewResponseDTO.ReviewSlice.class)))
    })
    public ApiResult<LimitedReviewResponseDTO.ReviewSlice> findAllMyReview(
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResult.onSuccess(mypageService.findAllByMemberWithLimitedImages(PageRequest.of(page-1, size)));
    }

    @GetMapping("/myreviews/detail")
    @Operation(description = "나의 리뷰 상세 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "나의 리뷰 상세 조회 성공",
            content = @Content(schema = @Schema(implementation = ReviewResponseDTO.ReviewSlice.class)))
    })
    public ApiResult<ReviewResponseDTO.ReviewSlice> findAllMyReviewDetail(
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam Long clickedReviewId) {
        return ApiResult.onSuccess(mypageService.findAllByMember(PageRequest.of(page-1, size), clickedReviewId));
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

    @GetMapping("/mydetail")
    public ApiResult<MyProfileResponseDTO> getMyProfile() {
        return ApiResult.onSuccess(mypageService.findMyProfile());
    }
}
